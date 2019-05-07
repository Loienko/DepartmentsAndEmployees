package net.ukr.dreamsicle.beans;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String createDate;


    public Employee() {

    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Employee(String name, String surname, String email, String createDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "name = " + name + ", surname = " + surname + ", email = " + email + ", createDate = " + createDate;
    }
}
