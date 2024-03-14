package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;

/**
 *
 * @author jalen
 */
public class Employee {

    private final int id;
    private final Badge badge;
    private final LocalDateTime active;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final Shift shift;
    private final EmployeeType description;
    private final Department department;

    //change constructor to hashmap instead of regular constructor
    public Employee(int id, String firstName, String lastName, String middleName, Shift shift, LocalDateTime active, EmployeeType description, Badge badge, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shift = shift;
        this.active = active;
        this.description = description;
        this.badge = badge;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public Badge getbadge() {
        return badge;
    }

    public LocalDateTime getactive() {
        return active;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getmiddleName() {
        return middleName;
    }

    public Shift getshift() {
        return shift;
    }

    public EmployeeType getdescription() {
        return description;
    }

    public Department getdepartment() {
        return department;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');

        return s.toString();
    }
}
