package edu.jsu.mcis.cs310.tas_sp24.dao;


import edu.jsu.mcis.cs310.tas_sp24.Employee;


import java.sql.*;
import java.util.HashMap;

public class EmployeeDAO {

    private static final String QUERY_FIND_1 = "SELECT * FROM employee where id = ?";
    private static final String QUERY_FIND_2 = "SELECT employeeid FROM employee where badgeid = ?";

    private final DAOFactory daoFactory;
    int employeetypeid; //ask if this is correct 1/2

    EmployeeDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
        
    }
    
    //first find method
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

            if(rs.next()){

                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("id", Integer.toString(rs.getInt("id")));
                parameters.put("firstName", rs.getString("firstname"));
                parameters.put("lastName", rs.getString("lastname"));
                parameters.put("middleName", rs.getString("middlename"));
                parameters.put("shiftid", Integer.toString(rs.getInt("shiftid")));
                parameters.put("active", rs.getTime(("active")).toString());
                
                parameters.put("employeetypeid", Integer.toString(rs.getInt(employeetypeid))); //2/2

                parameters.put("badgeid", rs.getString("badgeid"));
                parameters.put("departmentid", Integer.toString(rs.getInt("departmentid")));

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

    public Employee find(Badge badge){

        Employee employee = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_FIND_2);
            pst.setInt(1, badge.getId());
            rs = pst.executeQuery();
            if(rs.next()){

                int employeeid = rs.getInt("id");
                employee = find(id);
            }
        }
        catch (SQLException e) {
            
            throw new DAOException(e.getMessage(), e);
            
        }
        return employee;


    }

}
