/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * August 30, 2021
 * Scheduling System Application
 */
package View_Controller.Customers;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomer_Controller implements Initializable {
    public TextField idTxt;
    public TextField nameTxt;
    public TextField addressTxt;
    public TextField phoneTxt;
    public TextField postalTxt;
    public ComboBox<Countries> countryCombo;
    public ComboBox<FirstLevelDivisions> divisionCombo;

    /**
     * method to initialize when opening page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFields();
        autoID();
        setCombo();
    } // END initialize

    /**
     * method to generate next available ID
     */
    private void autoID() {
        ObservableList<Customers> list = DBCustomers.getAllCustomers();
        int count = 0;

        for (Customers c : list) {
            count++;
        } // END for
        count++;
        idTxt.setText(Integer.toString((count)));
    } // ENd autoID

    /**
     * method to disable text fields
     */
    private void disableFields() {
        idTxt.setDisable(true);
        divisionCombo.setDisable(true);
    }

    /**
     * method to go back to customers page
     * @param actionEvent pressing button
     * @throws IOException if exception occurs
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Customers/Customers.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Customers");
        stage.setScene(scene);
        stage.show();
    } // END cancel method

    /**
     * method to save customer and validate data.
     * @param actionEvent clicking save button
     * @throws IOException if something happens when changing pages
     * @throws SQLException if something happens when executing sql
     */
    public void save(ActionEvent actionEvent) throws SQLException, IOException {
        //check if empty
        if (idTxt.getText().isEmpty() ||
                nameTxt.getText().isEmpty() ||
                addressTxt.getText().isEmpty() ||
                postalTxt.getText().isEmpty() ||
                phoneTxt.getText().isEmpty() ||
                countryCombo.getSelectionModel().getSelectedItem() == null ||
                divisionCombo.getSelectionModel().getSelectedItem() == null ) {

            showAlert("Please complete all fields.");
            return;
        } // END if empty

        //get values
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalTxt.getText();
        String phone = phoneTxt.getText();
        int divID = divisionCombo.getValue().getDivision_ID();

        //Get other variables
        LocalDateTime created = LocalDateTime.now();
        Users user = Login_Controller.getCurrentUser();
        DBCustomers.createCustomer(name, address,postal, phone, created, user.getUser_Name(), user.getUser_Name(), divID);

        goToMain(actionEvent);

    } // ENd save method

    /**
     * method to show alerts
     * @param s String to set as Content Text
     */
    public void showAlert(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.showAndWait();
    } // END show alert


    /**
     * method to set up combo boxes
     */
    public void setCombo() {
        ObservableList<Countries> clist = DBCountries.getAllCountries();

        countryCombo.setItems(clist);
        countryCombo.setPromptText("Please choose a country");
    } // ENd setCombos


    /**
     * method to set Division combo
     * @param actionEvent selecting a choice on country combo box
     */
    public void setDivisionCombo(ActionEvent actionEvent) {
        divisionCombo.setDisable(false);
        divisionCombo.setPromptText("Please choose a division");
        Countries c = countryCombo.getSelectionModel().getSelectedItem();

        ObservableList<Countries> clist = DBCountries.getAllCountries();
        ObservableList<FirstLevelDivisions> divisions = DBDivisions.getAllDivisions();
        ObservableList<FirstLevelDivisions> dlist = FXCollections.observableArrayList();

        for (FirstLevelDivisions d: divisions) {
            if (d.getCountry_ID() == c.getCountry_ID()) {
                dlist.add(d);
            } // ENd if
        } // ENd for

        divisionCombo.setItems(dlist);

    } // END setDivisionCombo method

} // AddCustomer_Controller class
