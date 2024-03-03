package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PunchDAO {
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, Badge badge, EventType punchType) {

        
        String result = "[]";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "SELECT * FROM punch WHERE termid = ? AND badgeid = ? AND punchtypeid = ?";
        try {
            conn = daoFactory.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, termid);
            pst.setString(2, badge.getId());
            pst.setString(3, punchType.toString());
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getString("id");
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
        
        
        return result;
        
        
    }
    
}
