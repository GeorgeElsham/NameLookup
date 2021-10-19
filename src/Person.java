public class Person {
  public String name;
  public String email;
  public String telephone;

  public Person(String name, String email, String telephone) {
    this.name = name;
    this.email = email;
    this.telephone = telephone;
  }

  public void printDetails() {
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);
    System.out.println("Telephone: " + name);
  }
}
