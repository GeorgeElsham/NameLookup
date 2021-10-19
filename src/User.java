import java.net.*;
import java.io.*;
import java.util.*;

/**
 * User calling the queries.
 */
public class User {

  /**
   * Query for username. Searches for the exact username.
   *
   * @param username Username to query.
   * @return Returns person if found, null otherwise.
   */
  public Person query(String username) {
    // Get HTML page code for given user
    final List<String> html = getHtmlForUser(username);
    String[] lines = linesFromHtml(html);

    // If the HTML failed, bail out
    if (lines == null) {
      return null;
    }

    // Get user's details
    Map<String, String> details = getDetails(lines);

    // Return person object
    return personFromDetails(details);
  }

  /**
   * Gets entire HTML page for user.
   *
   * @param username Username to get page of.
   * @return List of every line of the HTML.
   */
  private List<String> getHtmlForUser(String username) {
    try {
      // Construct URL
      final String domain = "https://www.ecs.soton.ac.uk";
      final String endpoint = "/people/" + username;
      final URL url = new URL(domain + endpoint);

      // Create stream
      final InputStream response = url.openStream();
      final InputStreamReader inputReader = new InputStreamReader(response);
      BufferedReader bufferReader = new BufferedReader(inputReader);

      // Read lines
      List<String> lines = new ArrayList<>();
      while (bufferReader.ready()) {
        final String line = bufferReader.readLine();
        lines.add(line);
      }

      // Close and return all lines
      bufferReader.close();
      return lines;
    } catch (MalformedURLException e) {
      System.out.println("MalformedURLException error");
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      System.out.println("IOException error");
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Gets individual HTML lines.
   *
   * @param html HTML split by new lines.
   * @return Lines split by HTML tags.
   */
  private String[] linesFromHtml(List<String> html) {
    // Make sure HTML is not null
    if (html == null) {
      return null;
    }

    // String prefix to search for in HTML
    final String search = "                            <nav typeof=\"BreadcrumbList\"";

    // Maybe the needed line
    final Optional<String> potentialLine = html.stream()
        .filter(x -> x.startsWith(search))
        .findFirst();

    // Check line exists
    if (potentialLine.isPresent()) {
      // Get relevant part of line
      String line = potentialLine.get();
      line = line.substring(28);

      // Split line by HTML tags
      String[] details = line.split("> *<");

      // Only care about lines with a property label
      details = Arrays.stream(details)
          .filter(x -> x.contains("property"))
          .toArray(String[]::new);

      // Return relevant lines
      return details;
    } else {
      return null;
    }
  }

  /**
   * Get properties and their values. These are the details about a person.
   *
   * @param lines Lines for each HTML tag.
   * @return Get the details for each property.
   */
  private Map<String, String> getDetails(String[] lines) {
    // Create hash map to link properties to values
    Map<String, String> details = new HashMap<>();

    // Iterate all lines to populate hash map
    for (String item : lines) {
      // Get property name
      final int propertyIndex = item.indexOf("property");
      final char delimiter = item.charAt(propertyIndex + 9);
      final int propertyEndIndex = item.indexOf(delimiter, propertyIndex + 10);
      final String property = item.substring(propertyIndex + 10, propertyEndIndex);

      // Get property value
      final int closeIndex = item.indexOf(">", propertyIndex + 11);
      final int openIndex = item.indexOf("<", closeIndex + 1);
      if (Math.min(closeIndex, openIndex) == -1) {
        continue;
      }
      final String value = item.substring(closeIndex + 1, openIndex);

      // Add key-value pair to hash map
      details.put(property, value);
    }

    System.out.println(details);

    // Return hash map
    return details;
  }

  /**
   * Create a person from the details found.
   *
   * @param details Dictionary of all the details.
   * @return Person object.
   */
  private Person personFromDetails(Map<String, String> details) {
    final String name = details.get("name");
    final String email = details.get("email");
    final String telephone = details.get("telephone");
    return new Person(name, email, telephone);
  }
}
