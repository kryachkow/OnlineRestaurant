package com.onlinerestaurant.db;

import java.sql.SQLException;

public class DAOException extends SQLException {

    public DAOException(String message, SQLException cause) {
        super(message, cause);
    }

}
