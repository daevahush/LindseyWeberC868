package Controller;

import Model.Appointment;
import Model.Patient;
import Utilities.AppointmentUtilities;
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
    @FXML
    private TextField ApptTitleTxt;

    @FXML
    private TextField ApptDescriptionTxt;

    @FXML
    private TextField ApptLocationTxt;

    @FXML
    private TextField ApptTypeTxt;

    @FXML
    private TextField ApptStartTimeTxt;

    @FXML
    private TextField ApptEndDateTxt;

    @FXML
    private TextField ApptEndTimeTxt;

    @FXML
    private TextField ApptStartDateTxt;

    @FXML
    private TableView<Patient> CustomerTable;

    @FXML
    private TableColumn<Patient, String> CustNameCol;

    @FXML
    private TableColumn<Patient, String> CustAddressCol;

    @FXML
    private TableColumn<Patient, String> CustPhoneCol;

    @FXML
    private Button CancelButton;

    @FXML
    private Button SaveButton;

    Stage stage;
    Parent scene;
    private int appointmentID;
    private String originalStart;

    @FXML
    void OnClickCancel(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to go back? " +
                "Any changes won't be saved");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            try {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
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
            String title = ApptTitleTxt.getText();
            String description = ApptDescriptionTxt.getText();
            String location = ApptLocationTxt.getText();
            String type = ApptTypeTxt.getText();
            String startTime = ApptStartTimeTxt.getText();
            String startDate = ApptStartDateTxt.getText();
            String endTime = ApptEndTimeTxt.getText();
            String endDate = ApptEndDateTxt.getText();

            Patient patient = CustomerTable.getSelectionModel().getSelectedItem();
            int customerID = patient.getPatientID();
            String customerName = patient.getPatientName();
            String start = startDate + " " + startTime;
            String end = endDate + " " + endTime;

            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
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
                AppointmentUtilities.updateAppointment(customerID, title, description, location, customerName, type, start, end, appointmentID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer contact");
            alert.showAndWait();

        }

    }

    public Patient selectCustomer(int customerID) throws SQLException {
        for(Patient patient : PatientUtilities.getPatient(customerID)) {
            return patient;
        }
        return null;
    }

    //Get appointment details from Main Screen Controller for which appointment record to update
    public void getAppointmentDetails(Appointment appointment, int appointmentId, int customerId, int rowIndex) {
        appointmentID = appointmentId;
        originalStart = appointment.getStart();

        ApptTitleTxt.setText(appointment.getTitle());
        ApptDescriptionTxt.setText(appointment.getDescription());
        ApptTypeTxt.setText(appointment.getType());
        ApptStartTimeTxt.setText(appointment.getStart().substring(11));
        ApptStartDateTxt.setText(appointment.getStart().substring(0,10));
        ApptEndTimeTxt.setText(appointment.getEnd().substring(11));
        ApptEndDateTxt.setText(appointment.getEnd().substring(0,10));

        //Populate Customer table with customer associated to appointment
        try {
            //Clears list of old data
            Patient.allPatients.clear();

            //Set Table
            CustomerTable.setItems(PatientUtilities.getAllPatients());
            CustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            CustAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            CustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            CustomerTable.getSelectionModel().select(selectCustomer(customerId));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
