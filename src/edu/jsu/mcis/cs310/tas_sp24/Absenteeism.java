package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author jalen
 */
public class Absenteeism {
    
    private final Employee employeeid;
    private final LocalDate payperiod;
    private final BigDecimal percentage;
    
    public Absenteeism(Employee employeeid, LocalDate payperiod, BigDecimal percentage) {
        
        this.employeeid = employeeid;
        this.payperiod = payperiod;
        this.percentage = percentage; 
        
    }
    
    public Employee getEmployeeid() {
    
        return employeeid;
        
    }
    
    public LocalDate getPayperiod() {
        
        return payperiod;
        
    }
    
    public BigDecimal getPercentage() {
        
        return percentage; 
        
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        
        s.append("# ").append(employeeid).append(" ");
        s.append("(Pay Period Starting ").append(payperiod).append(")");
        s.append(": ").append(percentage);
        
        return s.toString();
        
    }
    
}
