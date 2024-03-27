package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *
 * @author jalen
 */
public class Employee {

    private final int id;
    private final String badgeid;
    private final LocalDateTime active;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final int shiftid;
    private final int employeetypeid;
    private final int departmentid;

    //change constructor to hashmap instead of regular constructor
    public Employee(HashMap<String, String> parameters) {
        
        this.id = Integer.parseInt(parameters.get("id"));
        this.firstName = parameters.get("firstName");
        this.lastName = parameters.get("lastName");
        this.middleName = parameters.get("middleName");
        this.shiftid = Integer.parseInt(parameters.get("shiftid"));
        this.active = LocalDateTime.parse(parameters.get("active"));
        this.employeetypeid = Integer.parseInt(parameters.get("employeetypeid"));
        this.badgeid = parameters.get("badgeid");
        this.departmentid = Integer.parseInt(parameters.get("departmentid"));
        
    }

    public int getId() {
        
        return id;
        
    }

    public String getbadge() {
        
        return badgeid;
        
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

    public int getshiftid() {
        
        return shiftid;
        
    }

    public int getemployeeTypeid() {
        
        return employeetypeid;
        
    }

    public int getdepartmentid() {
        
        return departmentid;
        
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();

        s.append("ID #").append(id).append(" ");
        s.append(" ").append(lastName).append(", ");
        s.append("").append(firstName).append(" ");
        s.append("").append(middleName).append(" ");
        s.append("(").append(badgeid).append("), ");
        s.append("Type: ").append(employeetypeid).append(", ");
        s.append("Department: ").append(departmentid).append(", ");
        s.append("Active: ").append(active);

        return s.toString();
        
    }
}
