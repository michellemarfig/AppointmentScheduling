/**
 * @author Michelle Martinez-Figueroa
 * August 30, 2021
 * Scheduling System Application
 */
package View_Controller.Customers;

import DBAccess.DBCustomers;
import DBQuery.DBQuery;
import Database.DBConnection;
import Main.Scheduling_System;
import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Customers_Controller implements Initializable {

    public TableView<Customers> table;

    /**
     * method to initialize when page opens
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTable();
    } // ENd initialize

    /**
     * method to set table data
     */
    private void setTable() {
        table.setItems(DBCustomers.getAllCustomers());
        table.refresh();
    } // ENd setTable

    /**
     * method to go to Add Customer Page
     * @param actionEvent clicking add button
     * @throws IOException if something happens
     */
    public void goToAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Customers/AddCustomer.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    } // END goToAdd

    /**
     * method to go to Modify Customer Page
     * @param actionEvent clicking modify button
     * @throws IOException if something happens
     */
    public void goToModify(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Customers/ModifyCustomer.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Customer");
        stage.setScene(scene);
        stage.show();
    } // END goToAdd

    /**
     * mehtod to modify a customer
     * @param actionEvent clicking edit button
     * @throws IOException if something happens when changing pages
     */
    public void modify(ActionEvent actionEvent) throws IOException {
        if (table.getSelectionModel().getSelectedItem() == null) {
            Scheduling_System.showError("Please make a selection.");
            return;
        } // ENd if
            Customers c = table.getSelectionModel().getSelectedItem();

        ModifyCustomer_Controller.setCustomer(c);
        goToModify(actionEvent);
    } // ENd modify method

    /**
     * method to delete a customer
     * @param actionEvent clicking delete button
     */
    public void delete(ActionEvent actionEvent) {
        // if no selection
        if ((Customers) table.getSelectionModel().getSelectedItem() == null) {
            Scheduling_System.showError("Please make a selection.");
            return;
        } // END if

        //confirmation
        boolean b = Scheduling_System.showConf("Are you sure you want to delete this customer?");
        if (b == false) { return;}

        DBCustomers.deleteCustomer((Customers) table.getSelectionModel().getSelectedItem());
        //update table
        table.setItems(DBCustomers.getAllCustomers());
        table.refresh();

    } // ENd delete method

    /**
     * method to go to Dashboard
     * @param actionEvent clicking go to dashboard button
     * @throws IOException if something happens
     */
    public void goHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Dashboard.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // ENd goHome method
} // END Customers_Controller class
