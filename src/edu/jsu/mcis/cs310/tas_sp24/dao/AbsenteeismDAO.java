package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.*;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;

/**
 *
 * @author jalen / William
 */
public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE absenteeism SET percentage = ? WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_CLEAR = "DELETE FROM absenteeism WHERE employeeid = ?";

    private final DAOFactory daoFactory;

    AbsenteeismDAO(DAOFactory daofactory) {
        this.daoFactory = daofactory;
    }

    public Absenteeism find(Employee employee, LocalDate payperiod) {
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        payperiod = payperiod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());
                ps.setDate(2, Date.valueOf(payperiod));

                rs = ps.executeQuery();

                if (rs.next()) {

                    double percentage = rs.getDouble("percentage");

                    absenteeism = new Absenteeism(employee, payperiod, BigDecimal.valueOf(percentage));

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
        return absenteeism;
    }

    /**
     * Inserts a new Absenteeism object into the absenteeism table.
     *
     * @param absenteeism
     */
    public void create(Absenteeism absenteeism) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (find(absenteeism.getEmployee(), absenteeism.getPayperiod()) != null) {
                    ps = conn.prepareStatement(QUERY_UPDATE);
                    ps.setDouble(1, absenteeism.getPercentage().doubleValue());
                    ps.setInt(2, absenteeism.getEmployee().getId());
                    ps.setDate(3, Date.valueOf(absenteeism.getPayperiod()));
                } else {
                    ps = conn.prepareStatement(QUERY_CREATE);
                    ps.setInt(1, absenteeism.getEmployee().getId());
                    ps.setDate(2, Date.valueOf(absenteeism.getPayperiod()));
                    ps.setDouble(3, absenteeism.getPercentage().doubleValue());
                }

                ps.executeUpdate();

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

    }

    public void clear(Integer employeeid) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_CLEAR);
                ps.setInt(1, employeeid);

                ps.executeUpdate();

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

    }

}
