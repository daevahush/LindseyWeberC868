package Utilities;

import Database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Error {
    //Unexpected shutdown
    public static void unexpectedShutDown() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oof");
        alert.setContentText("The application needs to shut down because of an unexpected circumstance");
        alert.showAndWait().ifPresent(result -> {
            DBConnection.closeConnection();
            System.exit(-1);
        });
    }

    //Alert to confirm application close with user
    public static void exitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit");

        alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent(result -> {
            DBConnection.closeConnection();
            System.exit(0);
        });

    }

    //Error message for incorrect login credentials
    public static void logInFailed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Incorrect Username or Password");
        alert.showAndWait();
    }

    //Error message for invalid customer values entered when creating new customer
    public static void invalidValues() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please enter correct values when creating a new customer record. ");
        alert.showAndWait();
    }

    //Unable to delete when no item selected
    public static void unableToDeleteItem() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please select an item to delete that does not have an existing appointment");
        alert.showAndWait();
    }

    //Unable to schedule appointment during pre-occupied timeslot
    public static void unableToSchedule() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("There is already an appointment at this time. Please select another time to schedule");
        alert.showAndWait();
    }

    //Unable to schedule appointment outside of business hours
    public static void outsideBusinessHours() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please schedule appointments within business hours between 12:00 UTC and 21:00 UTC");
        alert.showAndWait();
    }

}
