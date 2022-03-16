package ru.ubrr.knutarev.security;


public class SympleSecurityFunction implements ComplexSecurityFunction, ScreenPrinter {
    public String encrypt(String decryptedData) {
        return new StringBuffer((String) decryptedData).reverse().toString();
    }
    public String decrypt(String encryptedData) {
        return new StringBuffer((String) encryptedData).reverse().toString();
    }
    public void info() {
        System.out.println("aaaaa");
    }
    public void printOnScreen() {
        System.out.println("Print on screen");
    }
    public String getHash(String val) {
        return val.length() + "";
    }

}
