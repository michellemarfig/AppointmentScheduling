/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * August 31, 2021
 * Scheduling System Application
 */
package View_Controller.Reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Reports_Controller {

    /**
     * method to go to dashboard
     * @param actionEvent clicking go to dashboard  button
     * @throws IOException if something happens changing pages
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Dashboard.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    } // END gotoLogin

    /**
     * method to go to summary
     * @param actionEvent clicking run summary button
     * @throws IOException if something happens changing pages
     */
    public void goToSummary(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Reports/Summary.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Summary");
        stage.setScene(scene);
        stage.show();
    } // END go to summary method

    /**
     * method to go to schedules
     * @param actionEvent clicking run schedules button
     * @throws IOException if something happens when changing pages
     */
    public void goToSchedules(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Reports/Schedules.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedules");
        stage.setScene(scene);
        stage.show();
    } // END go to schedules method

    /**
     * method to go to locations
     * @param actionEvent clicking run locations button
     * @throws IOException if something happens when changing pages
     */
    public void goToLocations(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/View_Controller/Reports/Locations.fxml")));
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Locations");
        stage.setScene(scene);
        stage.show();
    } // END go to locations method
} // END Reports class
