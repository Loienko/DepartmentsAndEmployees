package net.ukr.dreamsicle.beans;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Employee implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private int idDepartment;
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

    public Employee(int id, int idDepartment, String name, String surname, String email, String createDate) {
        this.id = id;
        this.idDepartment = idDepartment;
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

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", idDepartment=" + idDepartment +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
