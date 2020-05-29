package Controller;

import Model.Doctor;
import Model.Patient;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDoctorController implements Initializable {
    Stage stage;
    Parent scene;

    private int doctorID;

    //Initial Values
    private String startCity;
    private String startCountry;




    //    Get doctor details from Doctor Controller for which doctor record to update
    public void getDoctorDetails(Doctor doctor, int doctorId) {
        doctorID = doctorId;

//        NameTxt.setText(patient.getPatientName());
//        NumberTxt.setText(patient.getPhone());
//        AddressTxt.setText(patient.getAddress());
//        AddressTxt2.setText(patient.getAddress2());
//        CityTxt.setText(patient.getCity());
//        PostalCodeTxt.setText(patient.getPostalCode());
//        CountryTxt.setText(patient.getCountry());

//        startCity = CityTxt.getText();
//        startCountry = CountryTxt.getText();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
