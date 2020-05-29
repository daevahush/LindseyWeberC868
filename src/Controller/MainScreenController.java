package Controller;

import Model.*;
import Utilities.AppointmentUtilities;
import Utilities.Error;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Appointment> ApptTable;

    @FXML
    private TableColumn<Appointment, String> DateCol;

    @FXML
    private TableColumn<Appointment, String> StartTimeCol;

    @FXML
    private TableColumn<Appointment, String> EndTimeCol;

    @FXML
    private TableColumn<Appointment, String> DoctorCol;

    @FXML
    private TableColumn<Appointment, String> PatientCol;

    @FXML
    private Button ReportsBtn;

    @FXML
    private Button ExitBtn;

    @FXML
    private Button HomeBtn;

    @FXML
    private Button AppointmentsBtn;

    @FXML
    private Button PatientsBtn;

    @FXML
    private Button DoctorsBtn;

    //Navigation menu buttons
    @FXML
    void OnClickHome(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickAppointments(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickPatients(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/Patients.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickDoctors(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/Doctors.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickReports(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickExit(MouseEvent event) {
        Error.exitConfirmation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO change this to pull appointments only for current day
        //Populate Appointment table with the current day's Appointments
        try {
            Appointment.allAppointments.clear();

            ApptTable.setItems(AppointmentUtilities.getAllAppointment());
            DateCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            StartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            DoctorCol.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
            PatientCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
