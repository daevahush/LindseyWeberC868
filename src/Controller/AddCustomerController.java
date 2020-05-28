package Controller;

import Utilities.*;
import Utilities.Error;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField NameTxt;

    @FXML
    private TextField AddressTxt;

    @FXML
    private TextField NumberTxt;

    @FXML
    private TextField AddressTxt2;

    @FXML
    private TextField CityTxt;

    @FXML
    private TextField PostalCodeTxt;

    @FXML
    private TextField CountryTxt;

    @FXML
    private Button CancelButton;

    @FXML
    private Button SaveButton;

    Stage stage;
    Parent scene;

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
            } catch(IOException | NullPointerException e) {
                Error.unexpectedShutDown();
            }
        });

    }

    @FXML
    void OnClickSave(MouseEvent event) throws IOException, SQLException {
        String name = NameTxt.getText();
        String phoneNumber = NumberTxt.getText();
        String address = AddressTxt.getText();
        String address2 = AddressTxt2.getText();
        String city = CityTxt.getText();
        String postalCode = PostalCodeTxt.getText();
        String country = CountryTxt.getText();

        if(country.isEmpty() || city.isEmpty() || address.isEmpty() || postalCode.isEmpty() ||
                phoneNumber.isEmpty() || name.isEmpty()) {

            Error.invalidValues();

        } else {

//            Customer customer = new Customer();

            CountryUtilities.insertCountry(country);
            CityUtilities.insertCity(city, country);
            AddressUtilities.insertAddress(address, address2, city, postalCode, phoneNumber);
            PatientUtilities.insertCustomer(name, AddressUtilities.getAddressID(address, city));

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
