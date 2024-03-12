package edu.jsu.mcis.cs310.tas_sp24.dao;

public class DepartmentDAO {

    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
