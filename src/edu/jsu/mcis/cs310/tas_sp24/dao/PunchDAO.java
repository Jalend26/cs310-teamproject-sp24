package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.*;
import java.util.*;


//COMPLETTED by Qays
public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM `event` WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?,?,?,?)";
    
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

    //Completed by QAYS
    public int create(Punch punch) {
        
        int punchId = 0; // Default value to return if the operation fails
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            
            // Step 1: Authorize the Punch
            if (authorizePunch(punch)) {
                
                //set up the prepared statment
                conn = daoFactory.getConnection();
                pst = conn.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, punch.getTerminalId());
                
                pst.setString(2, punch.getBadge().getId());
                
                java.sql.Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
                LocalDateTime local = timestamp.toLocalDateTime();
                local = local.withNano(0);
                java.sql.Timestamp TS2 = java.sql.Timestamp.valueOf(local);
                pst.setTimestamp(3, TS2);
                
                pst.setInt(4, punch.getPunchType().ordinal());
                
                int affectedRows = pst.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating punch failed, no rows affected.");
                }
                
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    punchId = rs.getInt(1); // Retrieve the generated punch ID
                }
             
            }
        } 
        
        catch(SQLException e) {}
        
        return punchId; // Return the punch ID (or 0 if the operation failed)
        
    }
    
    private boolean authorizePunch(Punch punch){
        
        int ID_1 = punch.getTerminalId();
        Badge badge = punch.getBadge();
        Employee employee = daoFactory.getEmployeeDAO().find(badge);
        Department department = daoFactory.getDepartmentDAO().find(employee.getId());
        int ID_2 = department.getTerminalID();
        
        if(ID_1 == 0){
            return true;
        }
        else if(ID_2 == ID_1){
            return true;
        }
        return false;
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