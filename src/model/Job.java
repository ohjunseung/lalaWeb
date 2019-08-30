package model;

import java.io.Serializable;

public class Job implements Serializable {
    private String name, code;
    private double salary;

    public double getSalary() {
        return salary;
    }

    public Job(String name, String code, double salary) {
        this.name = name;
        this.code = code;
        this.salary = salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
