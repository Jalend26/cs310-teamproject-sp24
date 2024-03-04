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

    
}
