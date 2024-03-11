package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;
import java.util.HashMap;

//COMPLETED
public class ShiftDAO {
    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Shift find(int id) {
        Shift shift = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM shift WHERE id = ?";
        
        
        try {
            conn = daoFactory.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
        
            if (rs.next()) {
               HashMap<String, String> parameters = new HashMap<>();
                parameters.put("id", Integer.toString(rs.getInt("id")));
                parameters.put("description", rs.getString("description"));
                parameters.put("shiftstart", rs.getTime("shiftstart").toString());
                parameters.put("shiftstop", rs.getTime("shiftstop").toString());
                parameters.put("roundinterval", Integer.toString(rs.getInt("roundinterval")));
                parameters.put("graceperiod", Integer.toString(rs.getInt("graceperiod")));
                parameters.put("dockpenalty", Integer.toString(rs.getInt("dockpenalty")));
                parameters.put("lunchstart", rs.getTime("lunchstart").toString());
                parameters.put("lunchstop", rs.getTime("lunchstop").toString());
                parameters.put("lunchthreshold", Integer.toString(rs.getInt("lunchthreshold")));
                
                shift = new Shift(parameters);
            }
            
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
            return shift;
    }
    
    
    //ADD SECOND FIND METHOD
    public Shift find(Badge badge) throws SQLException {
        Shift shift = null;
        
        
        String query = "SELECT s.* FROM shift s JOIN employee e ON s.id = e.shiftid WHERE e.badgeid = ?";
        
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, badge.getId());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HashMap<String, String> parameters = new HashMap<>();
                    
                    // Mapping the ResultSet to HashMap as expected by the Shift constructor
                    parameters.put("id", String.valueOf(rs.getInt("id")));
                    parameters.put("description", rs.getString("description"));
                    parameters.put("shiftstart", rs.getTime("start").toString()); 
                    parameters.put("shiftstop", rs.getTime("stop").toString()); 
                    parameters.put("lunchStart", rs.getTime("lunchstart").toString());
                    parameters.put("lunchStop", rs.getTime("lunchstop").toString());
                    parameters.put("roundInterval", String.valueOf(rs.getInt("interval")));
                    parameters.put("gracePeriod", String.valueOf(rs.getInt("grace")));
                    parameters.put("dockPenalty", String.valueOf(rs.getInt("dock")));
                    parameters.put("lunchDeduct", String.valueOf(rs.getInt("lunchdeduct")));
                    
                    // Create the Shift object
                    shift = new Shift(parameters);
                }
            } catch (SQLException e) {
                throw new DAOException("SQL Exception", e);
            }
       
        
        return shift;
    }
    }
}    