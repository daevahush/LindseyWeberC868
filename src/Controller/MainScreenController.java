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

    @FXML
    private TableView<Appointment> ApptTable;

    @FXML
    private TableColumn<Appointment, String> ApptStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> ApptEndTimeCol;

    @FXML
    private TableColumn<Appointment, String> ApptTypeCol;

    @FXML
    private Button ExitButton;

    @FXML
    private Button AllApptsButton;

    @FXML
    private Button MonthlyApptsButton;

    @FXML
    private Button WeeklyApptsButton;

    @FXML
    private Button ReportsButton;

    @FXML
    private Button AddApptButton;

    @FXML
    private Button UpdateApptButton;

    @FXML
    private Button DeleteApptButton;

    Stage stage;
    Parent scene;

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


    //Customer modification buttons

//    @FXML
//    void OnClickAddCustomer(MouseEvent event) throws IOException {
//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        scene = FXMLLoader.load(getClass().getResource("../View/AddCustomer.fxml"));
//        stage.setScene(new Scene(scene));
//        stage.show();
//    }
//
//    @FXML
//    void OnClickUpdateCustomer(MouseEvent event) throws IOException {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("../View/UpdateCustomer.fxml"));
//            loader.load();
//
//            UpdateCustomerController controller = loader.getController();
//            Customer selectedItem = CustomerTable.getSelectionModel().getSelectedItem();
//            int customerId = selectedItem.getCustomerID();
//
//            controller.getCustomerDetails(selectedItem, customerId);
//
//            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//            Parent scene = loader.getRoot();
//            stage.setScene(new Scene(scene));
//            stage.show();
//
//        } catch (NullPointerException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Please select a customer record to update");
//            alert.showAndWait();
//        }
//
//    }
//
//    @FXML
//    void OnClickDeleteCustomer(MouseEvent event) {
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setContentText("Are you sure you want to delete this customer? ");
//
//        //This is a lambda function that saves a minuscule amount of memory
//        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
//            try {
//                Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
//                int customerId = customer.getCustomerID();
//
//                //Delete from DB
//                CustomerUtilities.deleteCustomer(customerId);
//
//                //Update tableview
//                Customer.allCustomers.clear();
//                CustomerTable.setItems(CustomerUtilities.getAllCustomers());
//            } catch (NullPointerException | SQLException e) {
//                Error.unableToDeleteItem();
//            }
//        });
//    }


    //Reporting button
    @FXML
    void OnClickReports(MouseEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //Exit application
    @FXML
    void OnClickExit(MouseEvent event) {
        Error.exitConfirmation();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Populate Appointment table with All Appointments
        try {
            Appointment.allAppointments.clear();

            ApptTable.setItems(AppointmentUtilities.getAllAppointment());
            ApptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            ApptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            ApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //Populate Customer table
//        try {
//            //Clears list of old data
//            Customer.allCustomers.clear();
//
//            CustomerTable.setItems(CustomerUtilities.getAllCustomers());
//            CustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
//            CustAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
//            CustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }

}
