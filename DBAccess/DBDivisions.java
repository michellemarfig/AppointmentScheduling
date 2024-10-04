/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * August 30, 2021
 * Scheduling System Application
 */
package DBAccess;

import Database.DBConnection;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBDivisions {
    /**
     * method to get all divisions
     * @return list of divisions
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        try {
            ObservableList<FirstLevelDivisions> list = FXCollections.observableArrayList();
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("last_Update");
                String lastBy = rs.getString("Last_Updated_By");
                int country = rs.getInt("Country_ID");

                FirstLevelDivisions d = new FirstLevelDivisions(id, name, created, createdBy, last, lastBy, country);
                list.add(d);
            } // ENd while
            return list;

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not get list of all divisions.");
            alert.showAndWait();
        } // END try-catch
        return null;
    } // END getAllCustomers method
} // END DBDivisions class
