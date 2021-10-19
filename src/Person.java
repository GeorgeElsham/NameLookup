/**
 * Object representing a person who is publicly visible.
 */
public class Person {
  public String name;
  public String email;
  public String telephone;

  public Person(String name, String email, String telephone) {
    this.name = name;
    this.email = email;
    this.telephone = telephone;
  }

  /**
   * Prints information about a person to the console.
   */
  public void printDetails() {
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);

    if (telephone != null) {
      System.out.println("Telephone: " + telephone);
    }
  }
}
