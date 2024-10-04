/**
 * @author Michelle Martinez-Figueroa
 * April 11, 2021
 * Scheduling System Application
 */


package Database;


import Main.Scheduling_System;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07p1Y";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static final String username = "U07p1Y";
    private static final String password = "53689094266";
    private static Connection conn = null;

    /**
     * method to connect to database
     * @return connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        } catch (Exception e ) {
            Scheduling_System.showError("Connection unsuccessful!");
        } // END try catch


        return conn;
    } // END startConnection method

    /**
     * method to close connection
     */
    public static void closeConnection () {
        try {
            conn.close();
        } catch (Exception e){
            // do nothing here
        } // END try catch
    } // END closeConnection


    /**
     * method to get connection
     * @return connection
     */
    public static Connection getConnection() {
        return conn;
    } // END getConnection method

} //END DBConnection class
