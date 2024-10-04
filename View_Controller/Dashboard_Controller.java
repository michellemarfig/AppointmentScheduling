/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */
package View_Controller;

import DBAccess.DBAppointments;
import Main.Scheduling_System;
import Model.Appointments;
import View_Controller.Appointments.ModifyAppointment_Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Dashboard_Controller implements Initializable {

    public Label welcomeLbl;
    public ToggleGroup ViewRadios;
    public TableView<Appointments> table;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn typeCol;
    public TableColumn locationCol;
    public TableColumn descCol;
    public TableColumn contactCol;
    public TableColumn userCol;
    public TableColumn custCol;
    public TableColumn titleCol;
    public TableColumn idCol;
    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    /**
     * method to initialize when opening page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setData();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } // try-catch
    } // ENd initialize


    /**
     * method to set data on table
     * @throws SQLException if failure getting all appointments list
     */
    private void setData() throws SQLException {
        appointments = DBAppointments.getAllAppointments();
        table.refresh();
        welcomeLbl.setText("Welcome " + Login_Controller.getCurrentUser().getUser_Name().toUpperCase(Locale.ROOT));
        table.setItems(appointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    } // ENd seData method

    /**
     * method to go to login screen
     * @param actionEvent clicking logout button
     * @throws IOException if something happens changing pages
     */
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Login.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // END gotoLogin

    /**
     * method to view appointments for the next week
     * @param actionEvent selecting weekly radio
     */
    public void weekly(ActionEvent actionEvent) {
        ObservableList<Appointments> alist = appointments;
        ObservableList<Appointments> wlist = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();

        for ( Appointments a: alist) {
            if (a.getStart().isBefore(now.plusDays(7))) {
                wlist.add(a);
            } // END if
        }// ENd for

        table.setItems(wlist);
        table.refresh();
    } //End weekly method

    /**
     * method to view appointments for the next month
     * @param actionEvent selecting monthly radio button
     */
    public void monthly(ActionEvent actionEvent) {
        ObservableList<Appointments> aList = appointments;
        ObservableList<Appointments> mList = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();

        for ( Appointments a: aList) {
            if (a.getStart().isBefore(now.plusDays(30))) {
                mList.add(a);
            } // END if
        }// ENd for

        table.setItems(mList);
        table.refresh();
    } // END monthly method

    /**
     * method to go to add appointment page
     * @param actionEvent clicking new button
     * @throws IOException if something happens changing pages
     */
    public void add(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Appointments/AddAppointments.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("New Appointment");
        stage.setScene(scene);
        stage.show();
    } // END add method

    /**
     * method to modify appointment
     * @param actionEvent clicking modify button
     * @throws IOException if something happens when changing pages
     */
    public void modify(ActionEvent actionEvent) throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null) {
            Scheduling_System.showError("Please first select an appointment.");
            return;
        } // END if nothing selected
        ModifyAppointment_Controller.setAppointment(table.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Appointments/ModifyAppointment.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Edit Appointment");
        stage.setScene(scene);
        stage.show();
    } //END modify method

    /**
     * apppointment to delete appointments
     * @param actionEvent clicking cancel button
     * @throws SQLException if something happens executing sql
     */
    public void delete(ActionEvent actionEvent) throws SQLException {
        if (table.getSelectionModel().getSelectedItem() == null) {
            Scheduling_System.showError("Please first select an appointment.");
            return;
        } // END if nothing selected

        boolean b = Scheduling_System.showConf("Are you sure you want to delete this appointment?");
        Appointments selection = table.getSelectionModel().getSelectedItem();

        if (b == false) {
            return;
        } // END if

        boolean deleted = DBAppointments.deleteAppointment(selection);
        if (deleted) {
            appointments.remove(selection);
            Scheduling_System.showInfoAlert(selection.getAppointment_ID(), selection.getType());
        } // END if
    } //END delete method

    /**
     * method to go to manage customers page
     * @param actionEvent clicking manage customers button
     * @throws IOException if something happens changing pages
     */
    public void goToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Customers/Customers.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Customers");
        stage.setScene(scene);
        stage.show();
    } // END goToCustomers

    /**
     * method to go to Reports page
     * @param actionEvent clicking get reports button
     * @throws IOException if something happens when changing pages
     */
    public void getReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Reports/Reports.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    } // END getReports

    public void viewAll(ActionEvent actionEvent) throws SQLException {
        setData();
    } // END viewAll method
} // END dashboard controller class
