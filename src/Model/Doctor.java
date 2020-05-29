package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Doctor {

    private int doctorID;
    private String doctorName;

    public static ObservableList<Doctor> allDoctors = FXCollections.observableArrayList();
    public static ObservableList<Doctor> updateDoctor = FXCollections.observableArrayList();

    public Doctor(int doctorID, String doctorName) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
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
}
