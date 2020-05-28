package Controller;

import Model.User;
import Utilities.Error;
import Utilities.SystemUtilities;
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

public class LogInController implements Initializable {

    @FXML
    private TextField UsernameTxt;

    @FXML
    private PasswordField PasswordTxt;

    Stage stage;
    Parent scene;

    @FXML
    void OnClickExit(MouseEvent event) {
        Error.exitConfirmation();
    }

    @FXML
    void OnClickLogIn(MouseEvent event) throws IOException, SQLException {
        String userName = UsernameTxt.getText();
        String password = PasswordTxt.getText();

        boolean logInSuccessful = SystemUtilities.validateLogIn(userName, password);

        if(logInSuccessful) {
            //Store which user is logged in to system
            new User(userName);

            //Log login attempt to log file
            SystemUtilities.log(userName, logInSuccessful);

            //Display Main Screen if login successful
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {
            //Log login attempt to log file
            SystemUtilities.log(userName, logInSuccessful);

            //Error message for incorrect login credentials
            Error.logInFailed();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
