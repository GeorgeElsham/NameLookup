import java.util.*;

public class App {
  public static void main(String[] args) {
    final App app = new App();
    app.run();
  }

  private void run() {
    final User user = new User();
    askForUsername(user);
  }

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

  private String input(String message) {
    System.out.print(message);
    final Scanner scanner = new Scanner(System.in);
    final String line = scanner.nextLine();
    System.out.println();
    return line;
  }

  private boolean isValidUsername(String username) {
    return username.matches("^[a-z0-9]+$");
  }
}
