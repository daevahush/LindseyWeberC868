package Controller;

import Model.Appointment;
import Model.Doctor;
import Model.Patient;
import Utilities.AppointmentUtilities;
import Utilities.DoctorUtilities;
import Utilities.PatientUtilities;
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

public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    private int appointmentID;
    private String originalStart;


    @FXML
    private TextField TitleTxt;

    @FXML
    private TextField DescriptionTxt;

    @FXML
    private TextField TypeTxt;

    @FXML
    private TextField StartTimeTxt;

    @FXML
    private TextField EndDateTxt;

    @FXML
    private TextField StartDateTxt;

    @FXML
    private TextField EndTimeTxt;

    @FXML
    private Button CancelBtn;

    @FXML
    private Button SaveBtn;

    @FXML
    private TableView<Patient> PatientTable;

    @FXML
    private TableColumn<Patient, String> PatientNameCol;

    @FXML
    private TableColumn<Patient, String> PatientAddressCol;

    @FXML
    private TableColumn<Patient, String> PatientPhoneCol;

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
    private TableView<Doctor> DoctorTable;

    @FXML
    private TableColumn<Doctor, String> DoctorNameCol;

    @FXML
    private TableColumn<Doctor, String> DoctorPhoneCol;

    @FXML
    private TableColumn<Doctor, String> DoctorEmailCol;


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



    @FXML
    void OnClickCancel(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to go back? " +
                "Any changes won't be saved");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch(IOException e) {
                Error.unexpectedShutDown();
            }
        });
    }

    @FXML
    void OnClickSave(MouseEvent event) throws IOException, SQLException{
        try {
            String title = TitleTxt.getText();
            String description = DescriptionTxt.getText();
            String type = TypeTxt.getText();
            String startTime = StartTimeTxt.getText();
            String startDate = StartDateTxt.getText();
            String endTime = EndTimeTxt.getText();
            String endDate = EndDateTxt.getText();

            Patient patient = PatientTable.getSelectionModel().getSelectedItem();
            int patientID = patient.getPatientID();

            Doctor doctor = DoctorTable.getSelectionModel().getSelectedItem();
            int doctorID = doctor.getDoctorID();

            String patientName = patient.getPatientName();
            String start = startDate + " " + startTime;
            String end = endDate + " " + endTime;

            if(title.isEmpty() || description.isEmpty() || type.isEmpty() ||
                    startTime.isEmpty() || endTime.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {

                Error.invalidValues();

            } else if(!AppointmentUtilities.withinBusinessHours(start, end)) {
                //Check to see if user entered values are within operating hours
                //Output error message if appointment is outside business hours
                Error.outsideBusinessHours();

            } else if(AppointmentUtilities.overlappingUpdate(start, end, originalStart)) {
                //Check to see if user entered values are during pre-existing appointment times
                //Output error message if appointment already scheduled during this time
                Error.unableToSchedule();

            } else {
                AppointmentUtilities.updateAppointment(patientID, doctorID, title, description, patientName, type, start, end, appointmentID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a patient and doctor");
            alert.showAndWait();

        }

    }

    //TODO fix this to select the existing patient and doctor
    public Patient selectPatient(int patientID) throws SQLException {
        for(Patient patient : PatientUtilities.getPatient(patientID)) {
            return patient;
        }
        return null;
    }

    public Doctor selectDoctor(int doctorID) throws SQLException {
        for(Doctor doctor : DoctorUtilities.getDoctor(doctorID)) {
            return doctor;
        }
        return null;
    }

    //Get appointment details from Main Screen Controller for which appointment record to update
    public void getAppointmentDetails(Appointment appointment, int appointmentId, int patientId, int doctorId, int rowIndex) {
        appointmentID = appointmentId;
        originalStart = appointment.getStart();

        TitleTxt.setText(appointment.getTitle());
        DescriptionTxt.setText(appointment.getDescription());
        TypeTxt.setText(appointment.getType());
        StartTimeTxt.setText(appointment.getStart().substring(11));
        StartDateTxt.setText(appointment.getStart().substring(0,10));
        EndTimeTxt.setText(appointment.getEnd().substring(11));
        EndDateTxt.setText(appointment.getEnd().substring(0,10));

        //Populate Customer table with customer associated to appointment
        try {
            //Clears list of old data
            Patient.allPatients.clear();
            Doctor.allDoctors.clear();

            //Set Table
            PatientTable.setItems(PatientUtilities.getAllPatients());
            PatientNameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
            PatientAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            PatientPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            DoctorTable.setItems(DoctorUtilities.getAllDoctors());
            DoctorNameCol.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
            DoctorPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            DoctorEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

            PatientTable.getSelectionModel().select(selectPatient(patientId));
            DoctorTable.getSelectionModel().select(selectDoctor(doctorId));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
