package Controller;

import Model.Appointment;
import Model.Report;
import Utilities.AppointmentUtilities;
import Utilities.ReportUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTypesTable;

    @FXML
    private TableColumn<Appointment, String> TypeCol;

    @FXML
    private TableColumn<Appointment, String> MonthCol;

    @FXML
    private TableColumn<Appointment, Integer> AmountCol;


    @FXML
    private TableView<Report> consultantTable;

    @FXML
    private TableColumn<Report, String> ConsultantNameCol;

    @FXML
    private TableColumn<Report, String> ApptTimeCol;


    @FXML
    private TableView<Report> TotalCustomerTable;

    @FXML
    private TableColumn<Report, Integer> CountCol;

    @FXML
    private TableColumn<Report, String> CustomerNameCol;

    @FXML
    private Button BackButton;

    Stage stage;
    Parent scene;

    @FXML
    void OnClickBack(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Appointment Types
        try {
            //Clears list of old data
            Appointment.appointmentTypes.clear();

            AppointmentTypesTable.setItems(AppointmentUtilities.appointmentTypesByMonth());
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            AmountCol.setCellValueFactory(new PropertyValueFactory<>("totalInMonth"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Consultant Schedules
        try {
            //Clears list of old data
            Report.consultantSchedules.clear();

            consultantTable.setItems(ReportUtilities.consultantSchedules());
            ConsultantNameCol.setCellValueFactory(new PropertyValueFactory<>("consultantName"));
            ApptTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Total Customers
        try {
            //Clears list of old data
            Report.totalCustomers.clear();

            TotalCustomerTable.setItems(ReportUtilities.getTotalCustomers());
            CountCol.setCellValueFactory(new PropertyValueFactory<>("totalCustomerCount"));
            CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
