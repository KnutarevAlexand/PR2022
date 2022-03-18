package ru.ubrr.knutarev.CloneDemo;

public class Person {
    private int age;
    private String name;
    public Person(int age, String name){
        this.age=age;
        this.name=name;
    }
    // конструктор копии
    public Person(Person other) {
        this(other.getAge(), other.getName());
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Person original = new Person(18, "Grishka");
        Person clone = new Person(original);
        System.out.println(original);
        System.out.println(clone);
    }
}