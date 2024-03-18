package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.util.HashMap;

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
    public Employee(HashMap<String, String> parameters) {
        
        this.id = Integer.parseInt(parameters.get("id"));
        this.firstName = parameters.get("firstName");
        this.lastName = parameters.get("lastName");
        this.middleName = parameters.get("middleName");
        this.shift = Shift.parse(parameters.get("shift"));
        this.active = LocalDateTime.parse(parameters.get("active"));
        this.description = EmployeeType.parse(parameters.get(description));
        this.badge = Badge.parse(parameters.get(badge));
        this.department = Department.parse(parameters.get(department));
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

        s.append("ID #").append(id).append(" ");
        s.append(" ").append(lastName).append(", ");
        s.append("").append(firstName).append(" ");
        s.append("").append(middleName).append(" ");
        s.append("(").append(badge).append("), ");
        s.append("Type: ").append(description).append(", ");
        s.append("Department: ").append(department).append(", ");
        s.append("Active: ").append(active);

        return s.toString();
        
        //" Active: " + active;
    }
}
