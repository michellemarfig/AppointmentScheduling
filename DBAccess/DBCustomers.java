/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */
package DBAccess;

import DBQuery.DBQuery;
import Database.DBConnection;
import Main.Scheduling_System;
import Model.Customers;
import Model.Users;
import View_Controller.Login_Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;


public class DBCustomers {

    /**
     * method to get all customers
     * @return list of all customers
     */
    public static ObservableList<Customers> getAllCustomers() {
        try {
            ObservableList<Customers> list = FXCollections.observableArrayList();
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("Last_Update");
                String lastBy = rs.getString("Last_Updated_By");
                int division = rs.getInt("Division_ID");

                Customers c = new Customers(id, name, address, postal, phone, created , createdBy, last, lastBy, division);
                list.add(c);
            } // ENd while
            return list;

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not get list of all customers.");
            alert.showAndWait();
        } // END try-catch
        return null;
    } // END getAllCustomers method

    /**
     * method to create a customer
     * @param name customer name
     * @param address address
     * @param postal postal code
     * @param phone phone
     * @param created date created
     * @param createdBy created by
     * @param lastBy last updated
     * @param divID division ID
     * @throws SQLException if something happens when executing sql
     */
    public static void createCustomer(String name, String address, String postal, String phone, LocalDateTime created, String createdBy, String lastBy, int divID) throws SQLException {
        try {
            Users user = Login_Controller.getCurrentUser();

            ZoneId utcID = ZoneId.of("UTC");
            ZonedDateTime createdLocal = created.atZone(TimeZone.getDefault().toZoneId());
            ZonedDateTime createdUTC = createdLocal.withZoneSameInstant(utcID);
            LocalDateTime createdLDT = createdUTC.toLocalDateTime();

            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Updated_By, Division_ID )" +
                    " VALUES ( '" + name + "', '" + address + "', '" + postal + "', '" + phone + "', '" + createdLDT + "', '" + createdBy + "', '" + lastBy + "', '" + divID + "');";

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);


        } catch (Exception e) {
            Scheduling_System.showError("Couldn't execute statement.");
        } // END try- catch

    } // END createCustomer method

    /**
     * method to update customer
     * @param id customer id
     * @param name name
     * @param address address
     * @param postal postal
     * @param phone phone
     * @param divID division ID
     * @throws SQLException if problem executing sql
     */
    public static void updateCustomer(int id, String name, String address, String postal, String phone, int divID) throws SQLException {
        try {
            Users user = Login_Controller.getCurrentUser();

            String sql = "UPDATE customers SET Customer_Name = '" + name + "', Address = '" + address + "', Postal_Code = '" + postal + "', Phone = '" + phone +
                            "', Last_Updated_By = '" + user.getUser_Name() + "', Division_ID = '" + divID + "' WHERE Customer_ID = '" + id + "';";

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);

        } catch (Exception e) {
            Scheduling_System.showError("Couldn't execute statement.");
        } // ENd try - catch
    } // END updateCustomer

    /**
     * method to delete customer
     * @param c customer object
     */
    public static void deleteCustomer(Customers c) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID=" + c.getCustomer_ID();

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);

        } catch (Exception e) {
            Scheduling_System.showError("There are upcoming appointments for this customer. Please first delete appointments and try again.");
        } // END try- catch
    } // ENd deleteCustomers

} // END DBCustomers
