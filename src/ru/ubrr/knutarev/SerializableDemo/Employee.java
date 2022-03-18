package ru.ubrr.knutarev.SerializableDemo;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String empname;
    private byte empage;

    public String getEmpName() {
        return empname;
    }

    public void setEmpName(String empname) {
        this.empname = empname;
    }

    public byte getEmpAge() {
        return empage;
    }

    public void setEmpAge(byte empage) {
        this.empage = empage;
    }

    public String whoIsThis() {
        return getEmpName() + " is " + getEmpAge() + "years old";
    }
}