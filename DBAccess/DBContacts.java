/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */
package DBAccess;

import Database.DBConnection;
import Main.Scheduling_System;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBContacts {

    /**
     * method to get all contacts
     * @return list of all contacts
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> list= FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts c = new Contacts(id, name, email);
                list.add(c);

            } // END while
        } catch (Exception e) {
            Scheduling_System.showError("Couldn't get list of all contacts");
        } // END try-catch
        return list;
    } //END getAllContacts method
} //END DBContacts class
