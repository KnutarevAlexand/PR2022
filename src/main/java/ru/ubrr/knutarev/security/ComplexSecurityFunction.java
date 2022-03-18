package ru.ubrr.knutarev.security;

public interface ComplexSecurityFunction extends SecurityFunction {

    public String getHash(String val);
}
