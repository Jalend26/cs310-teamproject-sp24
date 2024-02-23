package edu.jsu.mcis.cs310.tas_sp24;

public class Punch {
    
    private final String termid, badge, punchType, id, localDateTime;
    
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
    
    @Override
    public String toString() {
        
        StringBuilder p = new StringBuilder();
        
        p.append('#').append(termid).append(' ');
        p.append('(').append(badge).append(')');
        p.append('(').append(punchType).append(')');
        
        return p.toString();
        
    }
}
