/**
 * @author Michelle Martinez-Figueroa
 * April 11, 2021
 * Scheduling System Application
 */


package Main;

import Database.DBConnection;
import Model.Appointments;
import View_Controller.Login_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Scheduling_System extends Application {

    /**
     * method to start application
     * @param primaryStage stage
     * @throws Exception if something happens setting stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/Login.fxml")));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle(Login_Controller.rb.getString("Scheduling") + " " + Login_Controller.rb.getString("System"));
        } else {
            primaryStage.setTitle("Scheduling System");
        }// END if-else
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    } //END start method

    /**
     * main method
     * @throws SQLException if something happens executing sql
     */
    public static void main(String[] args) throws SQLException {
        Login_Controller.rb = ResourceBundle.getBundle("Main/Nat_fr", Locale.getDefault());

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    } // END main method

    /**
     * method to show error alerts
     * @param s String to set as Content Text
     */
    public static void showError(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.showAndWait();
    } // END show alert

    /**
     * method to show confirmation alerts
     * @param s String to set as Content Text
     * @return true if confirmation accepted
     */
    public static boolean showConf(String s){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, s, (ButtonType.OK), ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.CANCEL) {
            return false;
        } //eND if
        return true;
    } // END show alert

    /**
     * method to show Information Alert
     * @param id appointment id
     * @param type type of appointment
     */
    public static void showInfoAlert(int id, String type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Appointment " + id + " " + type + " was deleted.");
        alert.showAndWait();
    } // END showInfo

    /**
     * method to show Information alert
     * @param s String to display
     */
    public static void showInfoAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.showAndWait();
    } // END showInfo
} // END SchedulingSystem class


