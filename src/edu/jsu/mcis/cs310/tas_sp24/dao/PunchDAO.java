package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


//COMPLETTED
public class PunchDAO {
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
        
    }
    public Punch find(int id) {
        
        Punch punch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM `event` WHERE id = ?";

        try {
            
            // Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();
            // Prepare the SQL statement
            ps = conn.prepareStatement(query);
            // Set the ID parameter
            ps.setInt(1, id);
            // Execute the query
            rs = ps.executeQuery();       
            // If the result set is not empty
            if (rs.next()) {
                
                // Extract data from the result set
                int terminalId = rs.getInt("terminalid");
                String badgeId = rs.getString("badgeid");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime().withNano(0); 
                //withNano() will zero out the milliseconds to avoid rounding erros 
                int eventTypeId = rs.getInt("eventtypeid");
            
                // Convert eventtypeid to EventType
                EventType eventType = EventType.values()[eventTypeId]; 
            
                // Create a new Punch object
                //creating a DAO that will return the badgeID instead of creating a seperate badge object
                BadgeDAO badgeDao = daoFactory.getBadgeDAO();
                Badge badge = badgeDao.find(badgeId);
                punch = new Punch(id, terminalId, badge, timestamp, eventType);
                
            }
        } catch (SQLException e) {
            
            throw new DAOException("SQL Exception", e);
            
        } finally { 
            
            
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                
            } catch (SQLException ex) {
                
                throw new DAOException(ex.getMessage());
                
            }
        }

        return punch;
    }
}