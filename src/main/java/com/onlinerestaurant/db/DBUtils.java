package com.onlinerestaurant.db;

import com.onlinerestaurant.ContextListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtils {

    private static final Logger LOGGER = LogManager.getLogger(DBUtils.class.getName());

    private static DBUtils instance;

    public static synchronized DBUtils getInstance() {
        if (instance == null) {
            instance = new DBUtils();
        }
        return instance;
    }


    private DBUtils() {
        try {
            LOGGER.info("Creating DBUtils singleton");
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
                ds = (DataSource)envContext.lookup("jdbc/db");
        } catch (NamingException ex) {
            LOGGER.error("Cannot obtain data source");
            throw new IllegalStateException("Cannot obtain data source", ex);
        }
    }

    private final DataSource ds;


    public Connection getConnection() {
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOGGER.error("Cannot obtain connection");
            throw new IllegalStateException("Cannot obtain a connection", ex);
        }
        return con;
    }

}
