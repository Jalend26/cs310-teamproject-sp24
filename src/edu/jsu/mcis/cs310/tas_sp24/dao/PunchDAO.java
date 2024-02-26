package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;


public class PunchDAO {
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, Badge badge, EventType punchType) {
        
        String result = "[]";
        
        
        return null;
        
        
    }
    
}