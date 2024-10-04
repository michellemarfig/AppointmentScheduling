/**
 * @author Michelle Martinez-Figueroa
 * August 31, 2021
 * Scheduling System Application
 */
package View_Controller.Reports;

import DBAccess.DBAppointments;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Locations_Controller implements Initializable {
    public ListView list;

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
     * method to initialize when opening page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> aList = FXCollections.observableArrayList();
        ObservableList<String> sList = FXCollections.observableArrayList();

        try {
            aList = DBAppointments.getAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for ( Appointments a: aList) {
            sList.add(a.getLocation());
        } // END for

        list.setItems(sList);
    } // END initialize
} // END Locations_Controller class



