package edu.jsu.mcis.cs310.tas_sp24;

public class Punch {
    
    private final String termid, badge, punchType;
    
    public Punch(String termid, String badge, String punchType) {
        this.termid = termid;
        this.badge = badge;
        this.punchType = punchType;
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
    
    @Override
    public String toString() {
        
        StringBuilder p = new StringBuilder();
        
    }
}
