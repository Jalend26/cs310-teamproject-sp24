package edu.jsu.mcis.cs310.tas_sp24.dao;

public class DAOException extends RuntimeException {

    public DAOException(String message) {
        super(message);

    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}


