package Controller;

import Model.Patient;
import Utilities.Error;
import Utilities.PatientUtilities;
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

public class PatientsController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Patient> PatientTable;

    @FXML
    private TableColumn<Patient, String> NameCol;

    @FXML
    private TableColumn<Patient, String> AddressCol;

    @FXML
    private TableColumn<Patient, String> PhoneCol;

    @FXML
    private TableColumn<Patient, String> EmailCol;

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
    private TextField searchTxt;

    @FXML
    private Button AddApptBtn;

    @FXML
    private Button UpdateApptBtn;

    @FXML
    private Button DeleteApptButton;


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


    //Patient modification buttons
    @FXML
    void OnClickAdd(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/AddPatient.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickUpdate(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/UpdatePatient.fxml"));
            loader.load();

            UpdatePatientController controller = loader.getController();
            Patient selectedItem = PatientTable.getSelectionModel().getSelectedItem();
            int patientID = selectedItem.getPatientID();

            controller.getPatientDetails(selectedItem, patientID);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a patient record to update");
            alert.showAndWait();
        }

    }

    @FXML
    void OnClickDelete(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this patient? ");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                Patient patient = PatientTable.getSelectionModel().getSelectedItem();
                int patientID = patient.getPatientID();

                //Delete from DB
                PatientUtilities.deletePatient(patientID);

                //Update tableview
                Patient.allPatients.clear();
                PatientTable.setItems(PatientUtilities.getAllPatients());
            } catch (NullPointerException | SQLException e) {
                Error.unableToDeleteItem();
            }
        });

    }

    //TODO
    @FXML
    void onActionSearch(ActionEvent event) {

    }


    public void initialize(URL location, ResourceBundle resources) {

        //Populate Patient table
        try {
            //Clears list of old data
            Patient.allPatients.clear();

            PatientTable.setItems(PatientUtilities.getAllPatients());
            NameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
            AddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
