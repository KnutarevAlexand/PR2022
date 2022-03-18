package ru.ubrr.knutarev.security;

public interface SecurityFunction {

    public static void display() {
        System.out.println("CALL");
    }
    public String encrypt(String decryptedData);
    public String decrypt(String encryptedData);
    public void info();

    public default void trace(String s) { // Релизация метода в интерфейсе по умолчанию
        System.out.println("TRACE: ");
    }

}
