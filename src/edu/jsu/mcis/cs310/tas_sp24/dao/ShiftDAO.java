package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;
import java.util.HashMap;

public class ShiftDAO {
    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    //first find method
    public Shift find(int id) {
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
                parameters.put("id", Integer.toString(id));
                parameters.put("description", rs.getString("description"));
                parameters.put("shiftstart", rs.getString("shiftstart"));
                parameters.put("stop", rs.getString("shiftstop"));
                parameters.put("lunchStart", rs.getString("lunchstart"));
                parameters.put("lunchStop", rs.getString("lunchstop"));
                
                //----------------------------------------------
                //why is there an error for these variables?
                parameters.put("roundInterval",rs.getInt("roundInterval"));
                parameters.put("gracePerioid", rs.getInt("gracePeriod"));
                //add the rest/modified variables to this
                //----------------------------------------------
                
                return new Shift(parameters);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL Exception", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
        return null;
    }
    
    //ADD SECOND FIND METHOD
    public Shift find(Badge badge) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null; // Move the declaration outside the try block
    
        try {
            // Get a connection from the DAOFactory
            conn = daoFactory.getConnection(); // Assign the connection here
    
            // Check if the connection is valid
            if (conn.isValid(0)) {
                // find the shift ID by the badge ID
                ps = conn.prepareStatement(QUERY_BADGE);
                ps.setString(1, badge.getId());
    
                // Execute 
                boolean hasResults = ps.execute();
    
                // If there are results, process them
                if (hasResults) {
                    rs = ps.getResultSet();
    
                    // Move to first  
                    if (rs.next()) {
                        // Retrieve the shift ID matching the badge
                        int shiftId = rs.getInt("shiftid");
                        
                        // Use the existing find method to get the Shift object
                        shift = find(shiftId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("SQL Exception", e);
        } finally { 
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
        
        
        return shift;
    }
}
    

    
}
