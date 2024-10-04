/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */

package DBAccess;

import Database.DBConnection;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBUsers {
    /**
     * method to get all users
     * @return list of all users
     */
    public static ObservableList<Users> getAllUsers (){

        ObservableList<Users> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String user = rs.getString("User_Name");
                String pass = rs.getString("Password");
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("Last_Update");
                String lastBy = rs.getString("Last_Updated_By");

                Users U = new Users(id, user, pass, created, createdBy, last, lastBy);
                list.add(U);
            } // END while
        } catch (Exception e) {
            System.out.println("Couldn't get a list on all users");
            //e.printStackTrace();
        } // END try catch
        return list;
    } // END getAllUsers method
} // ENd DBUsers class
