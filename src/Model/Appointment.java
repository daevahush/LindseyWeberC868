package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    private int appointmentId;
    private int patientID;
    private String patientName;
    private int doctorID;
    private String doctorName;
    private int userId;
    private String title;
    private String description;
    private String contact;
    private String type;
    private String start;
    private String end;

    private int totalInMonth;
    private String month;

    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentTypes = FXCollections.observableArrayList();

    public Appointment(String type, String month, int totalInMonth) {
        this.type = type;
        this.month = month;
        this.totalInMonth = totalInMonth;
    }

    public Appointment(int appointmentId, int patientID, String patientName, int doctorID, String doctorName, int userId, String title, String description, String contact,
                       String type, String start, String end) {
        this.appointmentId = appointmentId;
        this.patientID = patientID;
        this.patientName = patientName;
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public int getTotalInMonth() {
        return totalInMonth;
    }

    public void setTotalInMonth(int totalInMonth) {
        this.totalInMonth = totalInMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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

    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }

    public static void addWeeklyAppointment(Appointment newAppointment) {
        weeklyAppointments.add(newAppointment);
    }

    public static void addMonthlyAppointment(Appointment newAppointment) {
        monthlyAppointments.add(newAppointment);
    }

    public static void addAppointmentTypes(Appointment newItemInReport) {
        appointmentTypes.add(newItemInReport);
    }

}
