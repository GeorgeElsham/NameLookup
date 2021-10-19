import java.util.*;

/**
 * Main app. Entrypoint of the program.
 */
public class App {
  public static void main(String[] args) {
    final App app = new App();
    app.run();
  }

  /**
   * Ran from main.
   */
  private void run() {
    final User user = new User();
    askForUsername(user);
  }

  /**
   * Prompt user for input, then return the details if they exist.
   *
   * @param user User object to call the methods.
   */
  private void askForUsername(User user) {
    // Get username input
    final String username = input("Enter username (e.g. pll): ");

    // Validate username
    if (isValidUsername(username)) {
      // Query and print results
      final Person person = user.query(username);

      // Check person exists
      if (person != null) {
        person.printDetails();
      } else {
        System.out.println("Username does not exist.");
      }
    } else {
      // Invalid username
      final String msg = String.format("Invalid username query '%s'.", username);
      System.out.println(msg);
    }
  }

  /**
   * Asks for console input.
   *
   * @param message Question to ask in input.
   * @return Text inputted.
   */
  private String input(String message) {
    System.out.print(message);
    final Scanner scanner = new Scanner(System.in);
    final String line = scanner.nextLine();
    System.out.println();
    return line;
  }

  /**
   * Checks if a username is valid with a regex.
   *
   * @param username Username string to validate.
   * @return Whether the username is valid.
   */
  private boolean isValidUsername(String username) {
    return username.matches("^[a-z0-9]+$");
  }
}
