/**
 * @author Michelle Martinez-Figueroa
 * April 11, 2021
 * Scheduling System Application
 */

package DBQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement;

    /**
     * method to set Statement
     * @param conn connection
     * @throws SQLException if something happens executing sql
     */
    public static  void setStatement(Connection conn) throws SQLException {
       statement = conn.createStatement();
    } // END setStatement method

    /**
     * method to get statement
     * @return statement
     */
    public static Statement getStatement() {
        return statement;
    } // END getStatement
} // END DBQuery class
