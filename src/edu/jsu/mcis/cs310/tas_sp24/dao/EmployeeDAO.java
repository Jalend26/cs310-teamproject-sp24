package edu.jsu.mcis.cs310.tas_sp24.dao;


import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.sql.*;
import java.util.HashMap;

public class EmployeeDAO {

    private static final String QUERY_FIND_1 = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_2 = "SELECT id FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
        
    }
    
    //first find method - Completed by Qays (tests passed)
    public Employee find(int id) {

        Employee employee = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_FIND_1);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("id", rs.getInt("id")); 
                parameters.put("firstName", rs.getString("firstname"));
                parameters.put("lastName", rs.getString("lastname"));
                parameters.put("middleName", rs.getString("middlename"));
                parameters.put("active", rs.getTimestamp("active").toLocalDateTime()); 
                
                // Fetch and include the entire Badge object
                BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                Badge badge = badgeDAO.find(rs.getString("badgeid"));
                parameters.put("badge", badge); 
                
                // Fetch and include the entire Department object
                DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                Department department = departmentDAO.find(rs.getInt("departmentid"));
                parameters.put("department", department); 

                int employeetypeId = rs.getInt("employeetypeid");
                parameters.put("employeetypeid", employeetypeId); // Use the ID here, conversion in Employee constructor

                employee = new Employee(parameters);
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
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return employee;
    }

     //Second find method - Completed by Qays (tests passed)
    public Employee find(Badge badge){

        Employee employee = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_FIND_2);
            pst.setString(1, badge.getId());
            rs = pst.executeQuery();
            if(rs.next()){

                String id =(rs.getString("id"));
                int employeeid = Integer.parseInt(id);
                employee = find(employeeid);
            }
        }
        catch (SQLException e) {
            
            throw new DAOException(e.getMessage(), e);
            
        }
        return employee;


    }
}
