package net.ukr.dreamsicle.beans;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    private int id;
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё0-9-_]{1,50}")
    private String name_depart;
    @Min(0)
    private int count_employee;

    public Department() {
    }

    public Department(int id, String name_depart, int count_employee) {
        this.id = id;
        this.name_depart = name_depart;
        this.count_employee = count_employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                count_employee == that.count_employee &&
                Objects.equals(name_depart, that.name_depart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_depart, count_employee);
    }

    public String getName_depart() {
        return name_depart;
    }

    public void setName_depart(String name_depart) {
        this.name_depart = name_depart;
    }

    public int getCount_employee() {
        return count_employee;
    }

    public void setCount_employee(int count_employee) {
        this.count_employee = count_employee;
    }

    @Override
    public String toString() {
        return "name_depart = " + name_depart + ", count_employee = " + count_employee;
    }
}
