public class App {
  public static void main(String[] args) {
    final User user = new User();
    final Person person = user.query("pll");
    System.out.println(person.name);
  }
}
