/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */
package View_Controller.Appointments;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import Main.Scheduling_System;
import Main.lambda;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import View_Controller.Login_Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Main.Scheduling_System.showError;

public class AddAppointments_Controller implements Initializable {
    public TextField idTxt;
    public TextField titleTxT;
    public TextField descTxt;
    public ComboBox<Contacts> contactCombo;
    public TextField locationTxt;
    public TextField typeTxt;
    public DatePicker startDP;
    public DatePicker endDP;
    public ComboBox<Customers> custCombo;
    public ComboBox<LocalTime> endCombo;
    public ComboBox<LocalTime> startCombo;
    public ComboBox<Users> userCombo;

    /**
     * method to initialize when page opens
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDisable();
        try {
            autoID();
        } catch (SQLException e) {
            e.printStackTrace();
        } // END try-catch

        setCombos();
    } // END initialize

    /**
     * method to setup combo boxes
     */
    private void setCombos() {
        contactCombo.setItems(DBContacts.getAllContacts());
        custCombo.setItems(DBCustomers.getAllCustomers());
        userCombo.setItems((DBUsers.getAllUsers()));
        contactCombo.setPromptText("Please select a contact:");
        custCombo.setPromptText("Please select a customer:");
        userCombo.setPromptText("Please select a user:");

        //for time combo boxes
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);

        while (start.isBefore(end.plusSeconds(1))) {
            startCombo.getItems().add(start);
            endCombo.getItems().add(start);
            start = start.plusMinutes(15);
        } // END while
    } // ENd setCombos

    /**
     * method to autogenerate ID
     * @throws SQLException if something happens getting all appointments
     */
    private void autoID() throws SQLException {
        int nextID = 0;
        for (Appointments a: DBAppointments.getAllAppointments()) {
            if ( a != null) {
                nextID = a.getAppointment_ID();
            } // END if
        } // END for

        idTxt.setText(Integer.toString(++nextID));
    } // END autoID

    /**
     * method to disable appointment ID field
     */
    private void setDisable() {
        idTxt.setDisable(true);
    } // END set Disable

    /**
     * method to go back to dashboard
     * @param actionEvent clicking cancel button
     * @throws IOException if something happens while changing pages
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Dashboard.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // ENd goToMain method

    /**
     * method to save appointment
     * @param actionEvent clicking the save button
     * @throws IOException if something happens
     * @throws SQLException if something happens when executing sql
     */
    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        //if empty
        if ( idTxt.getText().isEmpty() ||
                titleTxT.getText().isEmpty() ||
                descTxt.getText().isEmpty() ||
                locationTxt.getText().isEmpty() ||
                contactCombo.getSelectionModel().getSelectedItem() == null ||
                typeTxt.getText().isEmpty() ||
                startDP.getValue() == null ||
                endDP.getValue() == null ||
                startCombo.getSelectionModel().getSelectedItem() == null ||
                endCombo.getSelectionModel().getSelectedItem() == null  ||
                userCombo.getSelectionModel().getSelectedItem() == null) {

            showError("Please complete all fields.");
            return;
        } // END if empty

        //get values from user
        int id = Integer.parseInt(idTxt.getText());
        String title = titleTxT.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        int contactID = contactCombo.getValue().getContact_ID();
        String type = typeTxt.getText();
        LocalDate startDate = startDP.getValue();
        LocalDate endDate = endDP.getValue();
        LocalTime startTime = startCombo.getSelectionModel().getSelectedItem();
        LocalTime endTime = endCombo.getSelectionModel().getSelectedItem();
        LocalDateTime start = LocalDateTime.of(startDate,startTime);
        LocalDateTime end = LocalDateTime.of(endDate,endTime);
        LocalDateTime created = LocalDateTime.now();
        String createdBy = Login_Controller.getCurrentUser().getUser_Name();
        String lastBy = Login_Controller.getCurrentUser().getUser_Name();
        int custID = custCombo.getValue().getCustomer_ID();
        int userID = userCombo.getValue().getUser_ID();

        if (checkBusinessHours(start, end) == false) {
            return;
        } // END if

        if (checkOverlapping(start, end, custID) == true) {
            return;
        } //END

        DBAppointments.createAppointment(id, title, description, location, type, start, end, created, createdBy, lastBy, custID, userID, contactID);

        goToMain(actionEvent);

    } // ENd save method

    /**
     * method to if appointment is within business hours
     * @param start start time chosen for appointment
     * @param end end time chosen for appointment
     * @return true if appointment is within business hourstest
     */
    public static boolean checkBusinessHours(LocalDateTime start, LocalDateTime end) {
        //convert start to end to to est
        ZoneId estID = ZoneId.of("America/New_York");
        ZonedDateTime startZDT = start.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime startEST = startZDT.withZoneSameInstant(estID);
        ZonedDateTime endZDT = end.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime endEST = endZDT.withZoneSameInstant(estID);

        //get times
        LocalTime startTime = startEST.toLocalTime();
        LocalTime endTime = endEST.toLocalTime();

        LocalTime open = LocalTime.of(8,0);
        LocalTime close = LocalTime.of(22, 00);

        if(startTime.isBefore(open) || endTime.isAfter(close)) {
            showError("Business hours are 8 AM to 10 PM EST.");
            return false;
        }// END
        return true;
    } // END checkBusinessHours

    /**
     * method to check if any appointments overlap
     * lambda function to show an alert for overlapping appointments
     * @param startLDT start
     * @param endLDT end
     * @param custID Customer ID
     * @return true if appointment overlaps
     * @throws SQLException if something happens while executing sql
     */
    private boolean checkOverlapping(LocalDateTime startLDT, LocalDateTime endLDT, int custID) throws SQLException {
        //get list off all appointments for this customer
        ObservableList<Appointments> aList = FXCollections.observableArrayList();
        for (Appointments a : DBAppointments.getAllAppointments()) {
            if (a.getCustomer_ID() ==  custID) {
                aList.add(a);
            } // END if
        } // ENd for

        //lambda function to show error alert in overlapping appointments
        //reduced code and improves readability
        lambda overlap = (i, j) -> {
            showError("Overlapping appointment: " + i+ " " + j);
        }; // END overlap

        //check if overlapping
        for (Appointments a: aList) {
            // if start is overlapping and end is after
            if (a.getStart().isAfter(startLDT) && a.getStart().isBefore(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            } // END if

            //if start is the same and end is after
            if (a.getStart().isEqual(startLDT) && a.getStart().isBefore(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            }// END if

            //if end is overlapping and start is before
            if (a.getEnd().isAfter(startLDT) && a.getEnd().isBefore(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            } //END if

            //if start is before and end is between appts
            if (a.getEnd().isAfter(startLDT) && a.getEnd().isEqual(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            } // END if

            //is start is before and end is after
            if (a.getStart().isBefore(startLDT) && a.getEnd().isAfter(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            } // END if

            //is both start and end are equal
            if (a.getStart().isEqual(startLDT) && a.getEnd().isEqual(endLDT)) {
                overlap.showOverlap(a.getAppointment_ID(), a.getType());
                return true;
            } // ENd if
        } // END for

        return  false;
    }// END check overlapping method
} // END AddAppointments_Controller class

