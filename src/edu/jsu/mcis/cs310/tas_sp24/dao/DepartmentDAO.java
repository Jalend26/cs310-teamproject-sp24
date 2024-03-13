package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.*;

public class DepartmentDAO {

    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Department find(int id) {
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, String.valueOf(id));
                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();
                    while (rs.next()) {
                        String description = rs.getString("description");
                        int terminalID = rs.getInt("terminalID");
                        department = new Department(id, description, terminalID);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            
        }
        return department;
    }
}
