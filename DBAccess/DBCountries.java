/**
 * @author Michelle Martinez-Figueroa
 * April 11, 2021
 * Scheduling System Application
 */

package DBAccess;

import Database.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBCountries {
    /**
     * method to return all countries
     * @return observable list of countries
     */
    public static ObservableList<Countries> getAllCountries() {
        //Create list
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("Last_Update");
                String lastBy = rs.getString("Last_Updated_By");


                Countries c = new Countries(id, name, created, createdBy, last, lastBy);
                clist.add(c);
            } // END while

        } catch (SQLException throwables) {
            System.out.println("Couldn't get list of all countries.");
        } // END catch

        return clist;
    } // END getAllCountries method

} // END class DBCountries
