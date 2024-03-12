package edu.jsu.mcis.cs310.tas_sp24.dao;

public class EmployeeDAO {

    private static final String QUERY_FIND = "SELECT * FROM employee WHERE id = ?";
    private final DAOFactory daoFactory;

    public EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
