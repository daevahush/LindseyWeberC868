package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Patient {
    private int patientID;
    private String patientName;
    private int addressID;
    private String address;
    private String address2;
    private int cityID;
    private String city;
    private int countryID;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    public static ObservableList<Patient> allPatients = FXCollections.observableArrayList();
    public static ObservableList<Patient> updatePatient = FXCollections.observableArrayList();

    public Patient(int patientID, String patientName, int addressID, String phone, String email, String address, String address2,
                   int cityID, String city, int countryID, String country, String postalCode) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.addressID = addressID;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.address2 = address2;
        this.cityID = cityID;
        this.city = city;
        this.countryID = countryID;
        this.country = country;
        this.postalCode = postalCode;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ObservableList<Patient> getAllPatients() {
        return allPatients;
    }

    public static void setAllPatients(ObservableList<Patient> allPatients) {
        Patient.allPatients = allPatients;
    }

    public static ObservableList<Patient> getUpdatePatient() {
        return updatePatient;
    }

    public static void setUpdatePatient(ObservableList<Patient> updatePatient) {
        Patient.updatePatient = updatePatient;
    }

    public static void addPatient(Patient newPatient) {
        allPatients.add(newPatient);
    }

    public static void addUpdatePatient(Patient newPatient) {
        updatePatient.add(newPatient);
    }

}
