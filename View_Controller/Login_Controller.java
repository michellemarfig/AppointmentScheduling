/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * April 11, 2021
 * Scheduling System Application
 */

package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import Main.Scheduling_System;
import Model.Appointments;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Login_Controller implements Initializable {
    public TextField UserTxt;
    public TextField PassTxt;
    private static Users currentUser;
    public Label userLbl;
    public Label passLbl;
    public Button loginBtn;
    public Label zoneLbl;
    public Label titleLbl;
    public static ResourceBundle rb;

    /**
     * method to initialize when opening page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
    } // END initialize method

    /**
     * method to set language
     */
    private void setLanguage() {
        zoneLbl.setText("ZoneID" + ": " + getZone());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            passLbl.setText(rb.getString("Password"));
            titleLbl.setText(rb.getString("Scheduling") + " " + rb.getString("System"));
            userLbl.setText(rb.getString("UserID"));
            loginBtn.setText(rb.getString("Login"));
            zoneLbl.setText(rb.getString("ZoneID") + ": " + getZone());
        } // ENd if
    } // END setLanguage

    /**
     * method to get the current user
     * @return current user object
     */
    public static Users getCurrentUser(){
        return currentUser;
    } // END get currentUser


    /**
     * method to login
     * @param actionEvent clicking login button
     * @throws IOException if something happens when changing screens
     * @throws SQLException if something happens when changing screens
     */
    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Users> list = DBUsers.getAllUsers();

        //If fields empty
        if (UserTxt.getText().isEmpty() || PassTxt.getText().isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                String s = rb.getString("Please") + " " + rb.getString("enter") + " " + rb.getString("a") + " " + rb.getString("Username") +
                        " " + rb.getString("and") + " " + rb.getString("Password") + ".";
                Scheduling_System.showError(s);
            } else {
                Scheduling_System.showError("Please enter a username and password.");
            } // END if else
            return;
        } // END if empty

        String username = UserTxt.getText();
        String password = PassTxt.getText();

        //check for matching user
        for (Users u: list) {
            if (u.getUser_Name().equals(username)) {
                //check for matching password
                if (u.getPassword().equals(password)) {
                    currentUser = u;
                    logActivity(currentUser);
                    checkForAppointments();
                    goToDashboard(actionEvent);
                    return;
                } // ENd if to check password
            } // END if to check userID
        } // END for
        logActivity(username);

        String s = "Incorrect Username and Password. Please try again.";
        if(Locale.getDefault().getLanguage().equals("fr")) {
            s = rb.getString("Incorrect") + " " + rb.getString("Username") + " " + rb.getString("and") + " " +
                    rb.getString("Password") + ". " + rb.getString("Please") + " " + rb.getString("try") + " " + rb.getString("again") + ".";
        } //END if
        Scheduling_System.showError(s);
    } // ENd login

    /**
     * method that checks for appointments in the next 15 minutes
     * @throws SQLException if something happens when executing sql
     */
    public void checkForAppointments() throws SQLException {
        LocalTime now = LocalTime.now();
        LocalTime window = LocalTime.now().plusMinutes(15);
        LocalTime start;

        for (Appointments a : DBAppointments.getAllAppointments()) {
            start = a.getStart().toLocalTime();

            if (a.getStart().toLocalTime().isAfter(LocalTime.now()) && a.getStart().toLocalTime().isBefore(window)) {
                Scheduling_System.showInfoAlert("Upcoming Appointment: " + a.getAppointment_ID() + " at " + a.getStart().toLocalTime() + " on " +
                        a.getStart().toLocalDate());
                System.out.println("upcoming appt");
                return;
            } // END if
        } // END for

        Scheduling_System.showInfoAlert("No upcoming appointments in next 15 minutes.");
    } // END check for appointment

    /**
     * method to go to dashboard
     * @param actionEvent clicking login button
     * @throws IOException if something happens when changing screens
     */
    private void goToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Dashboard.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // END goToDashboard method

    /**
     * method to get current zone id
     * @return zone id
     */
    public String getZone() {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneId zoneID = ZoneId.of(TimeZone.getDefault().getID());
        return zoneID.toString();
    } // ENd get Language method

    /**
     * Method to log successful login attempt
     * @param u current user
     * @throws IOException if something happens when creating or writing to file
     */
    public static void logActivity(Users u) throws IOException {
        File log = new File("login_activity.txt");
        log.createNewFile();

        String status = "Successful";

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime nowLocal = now.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime nowUTC = nowLocal.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime nowLDT = nowUTC.toLocalDateTime();

        FileWriter fw = new FileWriter(log, true);
        fw.write("Login Attempt: " + status + " " + nowLDT + " " + u.getUser_Name() + "\n");
        fw.close();
    } //END logActivity method


    /**
     * method to log unsuccessful login attempt
     * @param s attempted user name
     * @throws IOException if something happens when creating or writing to file
     */
    public static void logActivity(String s) throws IOException {
        File log = new File("login_activity.txt");
        log.createNewFile();

        String status = "Unsuccessful";

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime nowLocal = now.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime nowUTC = nowLocal.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime nowLDT = nowUTC.toLocalDateTime();

        FileWriter fw = new FileWriter(log, true);

        fw.write("Login Attempt: " + status + " " + nowLDT + " " + s+ "\n");
        fw.close();
    } //END logActivity method
} // END Main_Controller class
