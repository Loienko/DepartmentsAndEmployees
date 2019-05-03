package net.ukr.dreamsicle.beans;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String create_date;


    public Employee() {

    }

    public Employee(String name, String surname, String email, String create_date) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.create_date = create_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(surname, employee.surname) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(create_date, employee.create_date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, email, create_date);
    }

    public String getCreate_date() {

        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
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
        return "name = " + name + ", surname = " + surname + ", email = " + email + ", create_date = " + create_date;
    }
}
