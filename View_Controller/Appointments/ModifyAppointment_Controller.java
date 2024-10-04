/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * August 30, 2021
 * Scheduling System Application
 */
package View_Controller.Appointments;

import DBAccess.*;
import Main.Scheduling_System;
import Main.lambda;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
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
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Main.Scheduling_System.showError;

public class ModifyAppointment_Controller implements Initializable {
    public TextField idTxt;
    public TextField titleTxT;
    public TextField descTxt;
    public TextField locationTxt;
    public ComboBox<Contacts> contactCombo;
    public TextField typeTxt;
    public ComboBox<Customers> custCombo;
    public DatePicker startDP;
    public ComboBox<LocalTime> startCombo;
    public DatePicker endDP;
    public ComboBox<LocalTime> endCombo;
    private static Appointments currentAppointment;
    public ComboBox<Users> userCombo;

    /**
     * method to initialize when opening page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    } // END initialize method

    /**
     * method to set data
     */
    private void setData() {
        idTxt.setDisable(true);
        idTxt.setText(Integer.toString(currentAppointment.getAppointment_ID()));
        titleTxT.setText(currentAppointment.getTitle());
        descTxt.setText(currentAppointment.getDescription());
        locationTxt.setText(currentAppointment.getLocation());
        contactCombo.setItems(DBContacts.getAllContacts());
        typeTxt.setText(currentAppointment.getType());
        custCombo.setItems(DBCustomers.getAllCustomers());
        userCombo.setItems(DBUsers.getAllUsers());

        //for time combo boxes
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);

        while (start.isBefore(end.plusSeconds(1))) {
            startCombo.getItems().add(start);
            endCombo.getItems().add(start);
            start = start.plusMinutes(15);
        } // END while

        ZoneId localID = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startZDT = currentAppointment.getStart().atZone(localID);
        ZonedDateTime endZDT = currentAppointment.getEnd().atZone(localID);

        startDP.setValue(startZDT.toLocalDate());
        endDP.setValue(endZDT.toLocalDate());
        startCombo.setValue(startZDT.toLocalTime());
        endCombo.setValue(endZDT.toLocalTime());



        //set contact
        for (Contacts cont: DBContacts.getAllContacts() ) {
            if (currentAppointment.getContact_ID() == cont.getContact_ID()) {
                contactCombo.setValue(cont);
            } // END if
        } // END for

        // set customer
        for (Customers cust: DBCustomers.getAllCustomers()) {
            if (currentAppointment.getCustomer_ID() == cust.getCustomer_ID()) {
                custCombo.setValue(cust);
            } // END if
        } // END for

        //set user
        for (Users u: DBUsers.getAllUsers()) {
               if ( currentAppointment.getUser_ID() == u.getUser_ID()) {
                   userCombo.setValue(u);
               } // END for
        } // ENd for
    } // END setData

    /**
     * method to set current appointment
     * @param a appointment
     */
    public static void setAppointment(Appointments a) {
        currentAppointment = a;
    } // END


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
     * @param actionEvent clicking save button
     * @throws IOException if something happens
     * @throws SQLException if something happens when executing sql
     */
    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        if (titleTxT.getText().isEmpty() ||
                descTxt.getText().isEmpty() ||
                locationTxt.getText().isEmpty() ||
                contactCombo.getSelectionModel().getSelectedItem() == null ||
                typeTxt.getText().isEmpty() ||
                custCombo.getSelectionModel().getSelectedItem() == null ||
                startDP.getValue() == null ||
                startCombo.getSelectionModel().getSelectedItem() == null ||
                endDP.getValue() == null ||
                endCombo.getSelectionModel().getSelectedItem() == null ) {
            Scheduling_System.showError("Please complete all fields.");
        } // END if empty

        String title = titleTxT.getText();
        String desc = descTxt.getText();
        String location = locationTxt.getText();
        int contID = contactCombo.getValue().getContact_ID();
        String type = typeTxt.getText();
        int custID = custCombo.getValue().getCustomer_ID();
        int userID = userCombo.getValue().getUser_ID();

        LocalDateTime startLDT = LocalDateTime.of(startDP.getValue(), startCombo.getValue());
        LocalDateTime endLDT = LocalDateTime.of(endDP.getValue(), endCombo.getValue());

        if (!AddAppointments_Controller.checkBusinessHours(startLDT, endLDT)) {
            return;
        } // END if

        if (checkOverlapping(startLDT, endLDT, custID)) {
            return;
        } // END if

        DBAppointments.updateAppointment(currentAppointment.getAppointment_ID(), title, desc, location, type, startLDT, endLDT, custID, contID, userID);

        goToMain(actionEvent);

    } // END save method

    /**
     * method to check overlapping appointments
     * @param startLDT start
     * @param endLDT end
     * @param custID Customer ID
     * @return true if an appointment overlaps
     * @throws SQLException if something happens while executing sql
     */
    private boolean checkOverlapping(LocalDateTime startLDT, LocalDateTime endLDT, int custID) throws SQLException {
        //get list off all appointments for this customer excluding current appointment
        ObservableList<Appointments> aList = FXCollections.observableArrayList();
        for (Appointments a : DBAppointments.getAllAppointments()) {
            if (a.getCustomer_ID() ==  custID) {
                if (a.getAppointment_ID() != currentAppointment.getAppointment_ID()) {
                    aList.add(a);
                } //END if
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
                Scheduling_System.showInfoAlert("Overlapping appointment from" + a.getStart() + " to " + a.getEnd());
                return true;
            } // END if

            //if start is the same and end is after
            if (a.getStart().isEqual(startLDT) && a.getStart().isBefore(endLDT)) {
                Scheduling_System.showInfoAlert("Overlapping appointment from " + a.getStart() + " to " + a.getEnd());
                return true;
            }// END if

            //if end is overlapping and start is before
            if (a.getEnd().isAfter(startLDT) && a.getEnd().isBefore(endLDT)) {
                Scheduling_System.showInfoAlert("Overlapping appointment from " + a.getStart() + " to " + a.getEnd());
                return true;
            } //END if

            //if start is before and end is between appts
            if (a.getEnd().isAfter(startLDT) && a.getEnd().isEqual(endLDT)) {
                Scheduling_System.showInfoAlert("Overlapping appointment from " + a.getStart() + " to " + a.getEnd());
                return true;
            } // END if

            //is start is before and end is after
            if (a.getStart().isBefore(startLDT) && a.getEnd().isAfter(endLDT)) {
                Scheduling_System.showInfoAlert("Overlapping appointment from " + a.getStart() + " to " + a.getEnd());
                return true;
            } // END if

            //is both start and end are equal
            if (a.getStart().isEqual(startLDT) && a.getEnd().isEqual(endLDT)) {
                Scheduling_System.showInfoAlert("Overlapping appointment from " + a.getStart() + " to " + a.getEnd());
                return true;
            } // ENd if
        } // END for
        return  false;
    }// END check overlapping method
} // END modify class
