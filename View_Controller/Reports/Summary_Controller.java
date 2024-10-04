/**
 * @author Michelle Martinez-Figueroa
 * August 31, 2021
 * Scheduling System Application
 */
package View_Controller.Reports;

import DBAccess.DBAppointments;
import Main.lambda2;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class Summary_Controller implements Initializable {
    public Label totalLBL;
    public ComboBox<Month> monthCombo;
    public ComboBox<Year> yearCombo;
    public TextArea txtArea;
    private ObservableList<Month> months = FXCollections.observableArrayList();
    private ObservableList<Year> years = FXCollections.observableArrayList();


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
     * method to initialize when page is opened
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    } // END initialize method

    /**
     * method to set Data
     */
    private void setData() {
        Month month = Month.JANUARY;
        Year year = Year.of(2020);
        for(int i= 0; i < 12; i++) {
            months.add(month);
            month = month.plus(1);
        } // END for

        while (year.isBefore(Year.now().plusYears(1))) {
            years.add(year);
            year = year.plusYears(1);
        } // END

        yearCombo.setItems(years);
        monthCombo.setItems(months);
    } // END setData method

    /**
     * method to get appointment data
     * lambda function go to count how many appointments of each type
     * @param actionEvent clicking go button
     * @throws SQLException if something happens executing sql
     */
    public void getAppts(ActionEvent actionEvent) throws SQLException {
        txtArea.clear();
        ObservableList<Appointments> allAppts = DBAppointments.getAllAppointments();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        ObservableList<Integer> countList = FXCollections.observableArrayList();
        Year year = yearCombo.getValue();
        Month month = monthCombo.getValue();
        int yearInt = year.getValue();
        YearMonth ym = YearMonth.of(yearInt, month);

        //lambda function to avoid implementing another method
        // counts how many appointments with the same type
        lambda2 go = (type) -> {
            int count = 0;
            for( Appointments a : allAppts) {
                if(a.getType().equals(type)) {
                    count++;
                } // END if
            } // END for
            return count;
        }; // END lambda checkCount

        //loop to get all appointments in month and year
        for (Appointments a: allAppts) {
            if(a.getStart().getMonth().equals(ym.getMonth())){ // if month matches
                if (a.getStart().getYear() == ym.getYear()) { // if year matches
                    if(!typeList.contains(a.getType())) { // if not already on list
                        typeList.add(a.getType());              //then add type and count
                        countList.add(go.checkCount(a.getType())); //lambda
                    } // END if
                } // END if
            } // END if
        } // END for
        String s = "Type -> Count\n";
        for (int i = 0; i < typeList.size(); i++) {
            s = s + typeList.get(i) + " -> " + countList.get(i).toString() + "\n";
        } // END for

        totalLBL.setText(Integer.toString(typeList.size()));
        txtArea.setText(s);
    } // END getAppts
} // END Summary controller class
