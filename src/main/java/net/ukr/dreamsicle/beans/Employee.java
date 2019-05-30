package net.ukr.dreamsicle.beans;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private int id_department;
    @Size(max = 50)
    @Pattern(regexp = "\"[A-Za-zА-Яа-яЁё]{1,50}\"")
    private String name;
    @Size(max = 50)
    @Pattern(regexp = "\"[A-Za-zА-Яа-яЁё]{1,50}\"")
    private String surname;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    private String email;
    private String createDate;

    public Employee() {
    }

    public Employee(int id, int id_department, String name, String surname, String email, String createDate) {
        this.id = id;
        this.id_department = id_department;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.createDate = createDate;
    }

    public Employee(String name, String surname, String email, String createDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_department() {
        return id_department;
    }

    public void setId_department(int id_department) {
        this.id_department = id_department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", id_department=" + id_department +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
