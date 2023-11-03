package com.ttd.dtacoffee.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final String userName = "root";
    private static final String password = "123456";
    private static final String connectionURL = "jdbc:mysql://localhost:3306/dtacoffee";

    public static Connection openConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
