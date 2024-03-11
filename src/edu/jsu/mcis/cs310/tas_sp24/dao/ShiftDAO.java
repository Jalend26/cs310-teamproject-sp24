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
    public Shift find(Badge badge) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT shiftid FROM employee WHERE badgeid = ?";
        
        try{
            conn=daoFactory.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1,badge.getId());
            rs = pst.executeQuery();
                if (rs.next()) {
                    int shiftId = rs.getInt("shiftid");
                    // Use the first find method to get the Shift object
                    return find(shiftId);
                }
            }
        catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return null;
    }
}