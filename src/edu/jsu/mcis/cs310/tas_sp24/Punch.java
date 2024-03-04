package edu.jsu.mcis.cs310.tas_sp24;

// Punch Model Class Complete - Jalen

import java.time.LocalDateTime;


public class Punch {
    
    private LocalDateTime originalTime, adjustedTime;
    private final EventType punchType;
    private final int termid;
    private int id;
    private final Badge badge;
    private PunchAdjustmentType adjustmentType;
    
    
    public Punch(int termid, Badge badge, EventType punchType) {
        this.termid = termid;
        this.badge = badge;
        this.punchType = punchType;
        
    }
    
    public int gettermid() {
        return termid;
    }
    
    public Badge getbadge() {
        return badge;
    }
    
    public EventType getpunchType() {
        return punchType;
    }
    
    public Punch(int id, int termid, Badge badge, LocalDateTime originalTime, EventType punchType) {
        this.id = id;
        this.originalTime = originalTime;
        this.termid = termid;
        this.badge = badge;
        this.punchType = punchType;
    }
    
    public int getid(){
        return id;
    }
    public LocalDateTime getoriginalTime() {
        return originalTime;
    }
    
    public String printOriginal() {
        
        return "ID: " + id + "/nTermID: " + termid + "/nBadge: " + badge + "/nLocalDateTime: " + originalTime + "/nPunchType: " + punchType; 
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
        
    }
}
