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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomer_Controller implements Initializable {
    public TextField idTxt;
    public TextField nameTxt;
    public TextField addressTxt;
    public TextField phoneTxt;
    public TextField postalTxt;
    public ComboBox<FirstLevelDivisions> divisionCombo;
    public ComboBox<Countries> countryCombo;
    private static Customers currentCustomer;

    /**
     * method to initialize when page opens
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    } // ENd initialize

    /**
     * method to set current customer data
     */
    private void setData() {
        idTxt.setDisable(true);
        idTxt.setText(Integer.toString(currentCustomer.getCustomer_ID()));
        nameTxt.setText(currentCustomer.getCustomer_Name());
        addressTxt.setText(currentCustomer.getAddress());
        postalTxt.setText(currentCustomer.getPostal_Code());
        phoneTxt.setText(currentCustomer.getPhone());
        countryCombo.setItems(DBCountries.getAllCountries());
        divisionCombo.setItems(DBDivisions.getAllDivisions());

        //set division selection
        for (FirstLevelDivisions d : divisionCombo.getItems()) {
            if (currentCustomer.getDivision_ID() == d.getDivision_ID()) {
                divisionCombo.setValue(d);
            } // ENd if
        } // ENd for

        FirstLevelDivisions d = divisionCombo.getValue();
        for ( Countries c : countryCombo.getItems()) {
            if (c.getCountry_ID() == d.getCountry_ID()) {
                countryCombo.setValue(c);
            } // ENd if
        } // ENd for
    } // ENd setData method



    /**
     * method to set current customer
     * @param c current customer
     */
    public static void setCustomer(Customers c) {
        currentCustomer = c;
    } // END setCustomer


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

    /**
     * method to go to dashboard
     * @param actionEvent clicking a button
     * @throws IOException if something happens when changing pages
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Customers/Customers.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Manage Customers");
        stage.setScene(scene);
        stage.show();
    } // END goToMain

    /**
     * method to save new customer
     * @param actionEvent clicking save button
     * @throws IOException if something happens changing pages
     * @throws SQLException if something happens executing sql
     */
    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalTxt.getText();
        String phone = phoneTxt.getText();
        int divID = divisionCombo.getValue().getDivision_ID();

        DBCustomers.updateCustomer(id, name, address, postal, phone,divID);
        goToMain(actionEvent);
    } // END save method
}// END ModifyCustomer class
