package Controller;

import Model.Appointment;
import Utilities.AppointmentUtilities;
import Utilities.Error;
import com.sun.tools.javadoc.Start;
import javafx.event.ActionEvent;
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

public class AppointmentsController implements Initializable {
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

    @FXML
    private Button AllApptsBtn;

    @FXML
    private Button MonthlyApptsBtn;

    @FXML
    private Button WeeklyApptsBtn;

    @FXML
    private TextField SearchTxt;

    @FXML
    private Button AddApptBtn;

    @FXML
    private Button UpdateApptBtn;

    @FXML
    private Button DeleteApptBtn;


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


    //Appointment Table view buttons
    @FXML
    void OnClickAllAppts(MouseEvent event) throws SQLException {
        //Clears list of old data
        Appointment.allAppointments.clear();

        ApptTable.setItems(AppointmentUtilities.getAllAppointment());

    }

    @FXML
    void OnClickMonthlyAppts(MouseEvent event) throws SQLException {
        //Clears list of old data
        Appointment.monthlyAppointments.clear();

        ApptTable.setItems(AppointmentUtilities.getMonthAppointments());
    }

    @FXML
    void OnClickWeeklyAppts(MouseEvent event) throws SQLException {
        //Clears list of old data
        Appointment.weeklyAppointments.clear();

        ApptTable.setItems(AppointmentUtilities.getWeekAppointments());
    }

    //TODO
    @FXML
    void onActionSearch(ActionEvent event) {

    }


    //Appointment modification buttons
    @FXML
    void OnClickAddAppt(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickUpdateAppt(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController controller = loader.getController();
            Appointment selectedItem = ApptTable.getSelectionModel().getSelectedItem();
            int appointmentID = selectedItem.getAppointmentId();
            int customerID = selectedItem.getPatientID();
            int rowIndex = ApptTable.getSelectionModel().getSelectedIndex();

            controller.getAppointmentDetails(selectedItem, appointmentID, customerID, rowIndex);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an appointment to update");
            alert.showAndWait();
        }

    }

    @FXML
    void OnClickDeleteAppt(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this appointment? ");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                Appointment appointment = ApptTable.getSelectionModel().getSelectedItem();
                int appointmentId = appointment.getAppointmentId();

                //Delete from DB
                AppointmentUtilities.deleteAppointment(appointmentId);

                //Update tableview
                Appointment.allAppointments.clear();
                ApptTable.setItems(AppointmentUtilities.getAllAppointment());

            } catch (NullPointerException | SQLException e) {
                Error.unableToDeleteItem();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Populate Appointment table with All Appointments TODO adjust the doctor ID to pull name instead and the date column
        try {
            Appointment.allAppointments.clear();

            ApptTable.setItems(AppointmentUtilities.getAllAppointment());
            DateCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            StartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            EndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            DoctorCol.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
            PatientCol.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
