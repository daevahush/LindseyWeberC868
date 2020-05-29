package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Model.Patient;
import Utilities.*;
import Utilities.Error;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdatePatientController implements Initializable {

    Stage stage;
    Parent scene;

    private int customerID;

    //Initial Values
    private String startCity;
    private String startCountry;


    @FXML
    private Button CancelBtn;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField NameTxt;

    @FXML
    private TextField PhoneTxt;

    @FXML
    private TextField AddressTxt;

    @FXML
    private TextField AddressTxt2;

    @FXML
    private TextField CityTxt;

    @FXML
    private TextField PostalCodeTxt;

    @FXML
    private TextField CountryTxt;

    @FXML
    private TextField EmailTxt;

    @FXML
    private AnchorPane SaveBtn;

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


    @FXML
    void OnClickCancel(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to go back? " +
                "Any changes won't be saved");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/Patients.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch(IOException | NullPointerException e) {
                Error.unexpectedShutDown();
            }
        });

    }

    @FXML
    void OnClickSave(MouseEvent event) throws IOException, SQLException {
        String name = NameTxt.getText();
        String number = PhoneTxt.getText();
        String address = AddressTxt.getText();
        String address2 = AddressTxt2.getText();
        String city = CityTxt.getText();
        String postalCode = PostalCodeTxt.getText();
        String country = CountryTxt.getText();

        if(country.isEmpty() || city.isEmpty() || address.isEmpty() || postalCode.isEmpty() ||
                number.isEmpty() || name.isEmpty()) {

            Error.invalidValues();

        } else {
            CountryUtilities.updateCountry(startCountry, country);
            CityUtilities.updateCity(startCity, city);
            AddressUtilities.updateAddress(address, address2, city, postalCode);
            int addressID = AddressUtilities.getAddressID(address, city);
            System.out.println(addressID);
            PatientUtilities.updatePatient(customerID, name, addressID);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/Patients.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


    //    Get patient details from patient Controller for which patient record to update
    public void getPatientDetails(Patient patient, int customerId) {
        customerID = customerId;

        NameTxt.setText(patient.getPatientName());
        PhoneTxt.setText(patient.getPhone());
        AddressTxt.setText(patient.getAddress());
        AddressTxt2.setText(patient.getAddress2());
        CityTxt.setText(patient.getCity());
        PostalCodeTxt.setText(patient.getPostalCode());
        CountryTxt.setText(patient.getCountry());

        startCity = CityTxt.getText();
        startCountry = CountryTxt.getText();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
