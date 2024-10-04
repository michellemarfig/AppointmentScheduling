/**
 * @author Michelle Martinez-Figueroa
 * August 31, 2021
 * Scheduling System Application
 */
package View_Controller.Reports;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Schedules_Controller implements Initializable {


    public ComboBox<Contacts> combo;
    public TableView<Appointments> table;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custID;

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

    /**
     * method to go to dashboard
     * @param actionEvent clicking go to dashboard  button
     * @throws IOException if something happens changing pages
     */
    public void goToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Dashboard.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // END gotoLogin

    /**
     * method to get schedule
     * @param actionEvent making a selection on the combo box
     * @throws SQLException if something happens when executing SQL
     */
    public void getSchedule(ActionEvent actionEvent) throws SQLException {
        Contacts selection = combo.getValue();
        ObservableList<Appointments> allList = DBAppointments.getAllAppointments();
        ObservableList<Appointments> cList = FXCollections.observableArrayList();

        for (Appointments a: allList) {
            if (a.getContact_ID() == selection.getContact_ID()) {
                cList.add(a);
            } // ENd if
        } // ENd for
        table.setItems(cList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        custID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        table.refresh();

    } // END get Schedule method

    /**
     * method to initialize when page opens
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCombo();
    } // END initialize method

    /**
     * method to set combo box
     */
    private void setCombo() {
        combo.setItems(DBContacts.getAllContacts());
    }// END setCombo
} // END schedules class
