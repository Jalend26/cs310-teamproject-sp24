package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.time.LocalDateTime;
import java.sql.*;

public class EmployeeDAO {

    private static final String QUERY_FIND = "SELECT * FROM employee where id = ?";
    private static final String QUERY_FIND_1 = "SELECT * FROM employee where badgeid = ?";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        id = rs.getInt("id");
                        int employeetypeid = rs.getInt("employeetypeid");
                        String firstName = rs.getString("firstname");
                        String middleName = rs.getString("middlename");
                        String lastName = rs.getString("lastname");

                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        String badgeid = rs.getString("badgeid");
                        Badge badge = badgeDAO.find(badgeid);

                        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                        int departmentid = rs.getInt("departmentid");
                        Department department = departmentDAO.find(departmentid);

                        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
                        int shiftid = rs.getInt("shiftid");
                        Shift shift = shiftDAO.find(shiftid);

                        EmployeeType employeeType = EmployeeType.values()[employeetypeid];

                        employee = new Employee(id, firstName, middleName, lastName, badge, department, shift, employeeType);

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
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return employee;
    }
}
