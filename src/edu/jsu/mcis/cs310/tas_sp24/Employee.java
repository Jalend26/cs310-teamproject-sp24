package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


/**
 *
 * @author jalen
 * @completedBy Qays
 */
public class Employee {

    private final int id;
    private final Badge badge;
    private final LocalDateTime active;
    private final String firstName, lastName, middleName;
    private final Shift shift;
    private final EmployeeType employeetype; 
    private final Department department;
    

    //change constructor to hashmap instead of regular constructor
    //using <String, Object> instead of <String, String> to accomadate for Badge, Shift, Department object agruments in the Employee constructor
    public Employee(HashMap<String, Object> parameters) {
        
        //Cast object types for every parameter to fit into the <String, Object> format
        this.id = (Integer) parameters.get("id");
        this.firstName = (String) parameters.get("firstName");
        this.lastName = (String) parameters.get("lastName");
        this.middleName = (String) parameters.get("middleName");
        this.shift = (Shift) parameters.get("shift");
        this.active = (LocalDateTime) parameters.get("active");
        int employeetypeId = (Integer) parameters.get("employeetypeid");
        this.employeetype = EmployeeType.values()[employeetypeId];
        
        this.badge = (Badge) parameters.get("badge"); 
        this.department = (Department) parameters.get("department");
        
    }
    

    public int getId() {
        
        return id;
        
    }

    public Badge badge() {
        
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

    public Shift getShift() {
        
        return shift;
        
    }

    public EmployeeType getEmployeeType() {
        
    return employeetype;
    
}

    public Department getDepartment() {
        
        return department;
        
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        String format = this.active.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        
        
        s.append("ID #").append(id).append(":");
        s.append(" ").append(lastName).append(",");
        s.append(" ").append(firstName).append(" ");
        s.append("").append(middleName).append(" ");
        s.append("(#").append(badge.getId()).append("), ");
        s.append("Type: ").append(employeetype).append(", ");
        s.append("Department: ").append(department.getDescription()).append(", ");
        s.append("Active: ").append(format);

        return s.toString();
        
    }
}
