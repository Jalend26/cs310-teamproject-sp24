package edu.jsu.mcis.cs310.tas_sp24;

// Punch Model Class Complete - Jalen

public class Punch {
    
    private final String termid, badge, punchType, id, localDateTime;
    
    public Punch(String termid, String badge, String punchType) {
        this.termid = termid;
        this.badge = badge;
        this.punchType = punchType;
        this.id = null;
        this.localDateTime = null;
    }
    
    public String gettermid() {
        return termid;
    }
    
    public String getbadge() {
        return badge;
    }
    
    public String getpunchType() {
        return punchType;
    }
    
     public Punch(String id, String termid, String badge, String localDateTime, String punchType) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.termid = termid;
        this.badge = badge;
        this.punchType = punchType;
        
    }
    
    public String getid(){
        return id;
    }
    public String getlocalDateTime() {
        return localDateTime;
    }
    
    public String printOriginal() {
        
        return "ID: " + id + "/nTermID: " + termid + "/nBadge: " + badge + "/nLocalDateTime: " + localDateTime + "/nPunchType: " + punchType; 
    }
    
    @Override
    public String toString() {
        
        return printOriginal();
        
    }
}
