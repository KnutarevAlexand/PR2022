package ru.ubrr.knutarev.SerializableDemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Reader {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Employee employee = new Employee();
        FileInputStream fin = new FileInputStream("employee.obj");
        ObjectInputStream ois = new ObjectInputStream(fin);
        employee = (Employee) ois.readObject();
        ois.close();
        System.out.println(employee.whoIsThis());
    }
}