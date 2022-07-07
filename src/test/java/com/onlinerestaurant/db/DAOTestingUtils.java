package com.onlinerestaurant.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOTestingUtils {

    static private ScriptRunner scriptRunner;
    static private Connection con;

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/online_restaurant_test?serverTimezone=Europe/Kiev";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection setUpConnectionMethod(){
        BasicDataSource basicDataSource = Mockito.mock(BasicDataSource.class);
        DBUtils dbUtils = Mockito.mock(DBUtils.class);
        Whitebox.setInternalState(dbUtils,"instance", dbUtils);
        Whitebox.setInternalState(dbUtils, "ds", basicDataSource);

        try {
            con = DriverManager.getConnection(CONNECTION_URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void setUpDataBase(){
        scriptRunner = new ScriptRunner(con);
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader("sql/testing/createTestDB.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scriptRunner.runScript(reader);
        try {
            reader = new BufferedReader(new FileReader("sql/testing/testInsert.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scriptRunner.runScript(reader);
    }

    public static void clearDataBase() throws FileNotFoundException, SQLException {
        Reader reader = new BufferedReader(new FileReader("sql/testing/testDropSchema.sql"));
        scriptRunner.runScript(reader);
        con.close();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }
}
