package net.ukr.dreamsicle.beans;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {

    @NotNull
    private int id;
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё0-9-_]{1,50}")
    private String nameDepart;
    @Min(0)
    private int countEmployee;

    public Department() {
    }

    public Department(int id, String nameDepart, int countEmployee) {
        this.id = id;
        this.nameDepart = nameDepart;
        this.countEmployee = countEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                countEmployee == that.countEmployee &&
                Objects.equals(nameDepart, that.nameDepart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameDepart, countEmployee);
    }

    public String getName_depart() {
        return nameDepart;
    }

    public void setName_depart(String name_depart) {
        this.nameDepart = name_depart;
    }

    public int getCountEmployee() {
        return countEmployee;
    }

    public void setCountEmployee(int countEmployee) {
        this.countEmployee = countEmployee;
    }

    @Override
    public String toString() {
        return "nameDepart = " + nameDepart + ", countEmployee = " + countEmployee;
    }
}
