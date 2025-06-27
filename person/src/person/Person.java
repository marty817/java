package person;

public class Person {
	private String name;
    private String surname;
    private int age;
    public String password;

    public Person(String name, String surname, int age ,String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.password = password;
        
    }

    public String getpassword() {
        return password;
    }

    public Person setpassword(String password) {
        this.password = password;
        return this;
    }
    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Person setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

   
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}