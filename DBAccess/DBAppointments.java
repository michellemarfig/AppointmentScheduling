/**
 * @author Michelle Martinez-Figueroa
 * August 8, 2021
 * Scheduling System Application
 **/
package DBAccess;

import DBQuery.DBQuery;
import Database.DBConnection;
import Main.Scheduling_System;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class DBAppointments {

    /**
     * method to get all appointments
     *
     * @return list of all appointments
     * @throws SQLException if error happens
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp last = rs.getTimestamp("Last_Update");
                String lastBy = rs.getString("Last_Updated_By");
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(id, title, description, location, type, start, end, created, createdBy, last, lastBy, custID, userID, contID);
                alist.add(a);
            } // ENd while

        } catch (Exception e) {
            //Scheduling_System.showError("Couldn't get list of all appointments.");

        } // END try-catch
        return alist;
    } // ENd get all appointments

    /**
     * method to create appointment
     * @param id Appointment ID
     * @param title       Title
     * @param description Description
     * @param location    Location
     * @param type        Type
     * @param start       Start
     * @param end         End
     * @param created     Create Date
     * @param createdBy   Created By
     * @param lastBy      Last Updated By
     * @param custID      Customer ID
     * @param userID      User ID
     * @param contID      Contact ID
     */
    public static void createAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created,
                                         String createdBy, String lastBy, int custID, int userID, int contID) {

        ZoneId utcID = ZoneId.of("UTC");
        ZonedDateTime startLocal = start.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime startUTC = startLocal.withZoneSameInstant(utcID);
        ZonedDateTime endLocal = end.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime endUTC = endLocal.withZoneSameInstant(utcID);
        ZonedDateTime createdLocal = created.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime createdUTC = createdLocal.withZoneSameInstant(utcID);

        LocalDateTime startLDT = startUTC.toLocalDateTime();
        LocalDateTime endLDT = endUTC.toLocalDateTime();
        LocalDateTime createdLDT = createdUTC.toLocalDateTime();

        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Updated_By," +
                    " Customer_ID, User_ID, Contact_ID)" +
                    " VALUES ('" + id + "', '" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + startLDT + "', '" + endLDT + "', '" + createdLDT +
                    "', '" + createdBy + "', '" + lastBy + "', '" + custID + "', '" + userID + "', '" + contID + "');";

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);

        } catch (Exception e) {
            Scheduling_System.showError("Couldn't create appointment.");
            e.printStackTrace();
        } // END try- catch
    } // END createAppointment

    /**
     * method to delete appointment
     *
     * @param a Appointment object
     * @return true if appointment was deleted
     */
    public static boolean deleteAppointment(Appointments a) {
        int rowsAffected = 0;
        boolean result = false;
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID=" + a.getAppointment_ID();

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);
            rowsAffected = statement.getUpdateCount();
            if (rowsAffected > 0) {
                result = true;
            }
            return result;
        } catch (Exception e) {
            Scheduling_System.showError("Appointment not deleted. Try again.");
        } // END try- catch
        return result;
    } // END deleteAppointments

    /**
     * method to update appointment
     *
     * @param id       Appointment ID
     * @param title    Title
     * @param desc     Description
     * @param location Location
     * @param type     Type
     * @param start    Start
     * @param end      End
     * @param custID   Customer ID
     * @param contID   Contact ID
     * @param userID   User ID
     */
    public static void updateAppointment(int id, String title, String desc, String location, String type, LocalDateTime start, LocalDateTime end,
                                         int custID, int contID, int userID) {
        try {

            ZoneId utcID = ZoneId.of("UTC");
            ZonedDateTime startLocal = start.atZone(TimeZone.getDefault().toZoneId());
            ZonedDateTime startUTC = startLocal.withZoneSameInstant(utcID);
            ZonedDateTime endLocal = end.atZone(TimeZone.getDefault().toZoneId());
            ZonedDateTime endUTC = endLocal.withZoneSameInstant(utcID);

            LocalDateTime startLDT = startUTC.toLocalDateTime();
            LocalDateTime endLDT = endUTC.toLocalDateTime();

            String sql = "UPDATE appointments SET Title = '" + title + "', Description = '" + desc + "', Location = '" + location + "', Type = '" +
                    type + "', Start = '" + startLDT + "', End = '" + endLDT + "', Last_Updated_By = '" + userID +
                    "', Customer_ID = '" + custID + "', User_ID = '" + userID + "', Contact_ID = '" +
                    contID + "' WHERE Appointment_ID = '" + id + "';";

            DBQuery.setStatement(DBConnection.getConnection());
            Statement statement = DBQuery.getStatement();
            statement.execute(sql);

        } catch (Exception e) {
            Scheduling_System.showError("Appointment not updated. Try again.");
            e.printStackTrace();
        } // ENd try-catch


    } // END updateAppointment method

} // ENd appointments class
