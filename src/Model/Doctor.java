package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Doctor {

    private int doctorID;
    private String doctorName;
    private String phone;
    private String email;

    public static ObservableList<Doctor> allDoctors = FXCollections.observableArrayList();
    public static ObservableList<Doctor> updateDoctor = FXCollections.observableArrayList();

    public Doctor(int doctorID, String doctorName, String phone, String email) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.phone = phone;
        this.email = email;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public static ObservableList<Doctor> getAllDoctors() {
        return allDoctors;
    }

    public static void setAllDoctors(ObservableList<Doctor> allDoctors) {
        Doctor.allDoctors = allDoctors;
    }

    public static ObservableList<Doctor> getUpdateDoctor() {
        return updateDoctor;
    }

    public static void setUpdateDoctor(ObservableList<Doctor> updateDoctor) {
        Doctor.updateDoctor = updateDoctor;
    }

    public static void addDoctor(Doctor newDoctor) {
        allDoctors.add(newDoctor);
    }

    public static void addUpdateDoctor(Doctor newDoctor) {
        updateDoctor.add(newDoctor);
    }
}
