package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//COMPLETED by Qays

public class Punch {
    
    private int id;
    private final int terminalId;
    private final Badge badge;
    private final EventType punchType;
    private LocalDateTime originalTime; // Initialize in constructor for new punches
    private LocalDateTime adjustedTime; // Initially null, set later
    private PunchAdjustmentType adjustmentType = PunchAdjustmentType.NONE;

    // Constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType) {
        
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTime = LocalDateTime.now(); // Current system time
        
    }

    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTime, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTime = originalTime;
        this.punchType = punchType;
        
    }

    // Accessors
    public int getId() { 
        
        return id; 
    
    }
    public int getTerminalId() {
        
        return terminalId; 
        
    }
    public Badge getBadge() {
        
        return badge; 
        
    }
    public EventType getPunchType() {
        
        return punchType; 
        
    }
    public LocalDateTime getOriginalTime() {
        
        return originalTime; 
        
    }
    public LocalDateTime getAdjustedTime() {
        
        return adjustedTime; 
        
    }
    public PunchAdjustmentType getAdjustmentType() {
        
        return adjustmentType; 
        
    }

    // Setters for adjusted time and type
    public void setAdjustedTime(LocalDateTime adjustedTime) {
        
        this.adjustedTime = adjustedTime; 
        
    }
    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        
        this.adjustmentType = adjustmentType; 
        
    }
    
    public void adjust(Shift s){
        
    } 

    // Pretty print for original timestamp
    public String printOriginal() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = originalTime.format(formatter).toUpperCase();
        return String.format("#%s %s: %s", badge.getId(), punchType, formattedDate);
        
    }

    @Override
    public String toString() {
        
        return printOriginal(); // For now, this will print the original timestamp
        
    }
}
