package Controller;

import Model.Doctor;
import Utilities.DoctorUtilities;
import Utilities.Error;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorsController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private TableView<Doctor> DoctorTable;

    @FXML
    private TableColumn<Doctor, String> NameCol;

    @FXML
    private TableColumn<Doctor, String> PhoneCol;

    @FXML
    private TableColumn<Doctor, String> EmailCol;

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
    private TextField SearchTxt;

    @FXML
    private Button AddBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Button DeleteBtn;


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


    //Doctor modification buttons
    @FXML
    void OnClickAdd(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/AddDoctor.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnClickUpdate(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/UpdateDoctor.fxml"));
            loader.load();

            UpdateDoctorController controller = loader.getController();
            Doctor selectedItem = DoctorTable.getSelectionModel().getSelectedItem();
            int doctorID = selectedItem.getDoctorID();

            controller.getDoctorDetails(selectedItem, doctorID);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a doctor record to update");
            alert.showAndWait();
        }
    }

    @FXML
    void OnClickDelete(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this doctor? ");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                Doctor doctor = DoctorTable.getSelectionModel().getSelectedItem();
                int doctorID = doctor.getDoctorID();

                //Delete from DB
                DoctorUtilities.deleteDoctor(doctorID);

                //Update tableview
                Doctor.allDoctors.clear();
                DoctorTable.setItems(DoctorUtilities.getAllDoctors());
            } catch (NullPointerException | SQLException e) {
                Error.unableToDeleteItem();
            }
        });
    }

    //TODO
    @FXML
    void onActionSearch(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Populate Doctor table with All Doctors TODO uncomment this once doctor utils are up
//        try {
//            Doctor.allDoctors.clear();
//
//            DoctorTable.setItems(DoctorUtilities.getAllDoctors());
//            NameCol.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
//            PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//            EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }
}
