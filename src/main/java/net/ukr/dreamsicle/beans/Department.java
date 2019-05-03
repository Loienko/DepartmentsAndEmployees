package net.ukr.dreamsicle.beans;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    private int id;
    private String name_depart;
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

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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
