package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//COMPLETTED by Qays
public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM `event` WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
        
    }
    public Punch find(int id) {
        
        Punch punch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            
            // Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();
            // Prepare the SQL statement
            ps = conn.prepareStatement(QUERY_FIND);
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

    
    public int create(Punch punch) {
        
        int punchId = 0; // Default value to return if the operation fails
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            
            // Step 1: Authorize the Punch
            if (punch.getTerminalId() == 0 || authorizePunch(punch)) {
                
                conn = daoFactory.getConnection();
                String query = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
                //set up the prepared statment
                
            }
        } 
        
        catch(SQLException e) {}
        
        return punchId; // Return the punch ID (or 0 if the operation failed)
        
    }
    
    private boolean authorizePunch(Punch punch){
        
        return true;
    }
    
    
    // Existing find method and other members...

    public List<Punch> list(Badge badge, LocalDate day) {
        
        List<Punch> punches = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // This SQL query retrieves all punches for the specified badge on the specified day,
        // and the first punch of the next day if it is a 'clock out' or 'time out' punch.
        String query = "SELECT * FROM `event` WHERE badgeid = ? AND " +
                       "((DATE(timestamp) = ?) OR " +
                       "(DATE(timestamp) = ? AND eventtypeid IN (0, 2) AND " +
                       "id = (SELECT MIN(id) FROM `event` WHERE badgeid = ? AND DATE(timestamp) = ?))) " +
                       "ORDER BY timestamp ASC";

        try {
            Connection conn = daoFactory.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, badge.getId());
            ps.setDate(2, java.sql.Date.valueOf(day));
            ps.setDate(3, java.sql.Date.valueOf(day.plusDays(1)));
            ps.setString(4, badge.getId());
            ps.setDate(5, java.sql.Date.valueOf(day.plusDays(1)));
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int terminalId = rs.getInt("terminalid");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                EventType eventType = EventType.values()[rs.getInt("eventtypeid")];

                Punch punch = new Punch(id, terminalId, badge, timestamp, eventType);
                punches.add(punch);
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

        return punches;
    }

}