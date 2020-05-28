package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Report {

    private String consultantName;
    private String appointmentStart;
    private int totalCustomerCount;
    private String customerName;


    public static ObservableList<Report> consultantSchedules = FXCollections.observableArrayList();
    public static ObservableList<Report> totalCustomers = FXCollections.observableArrayList();

    public Report(int totalCustomerCount, String customerName) {
        this.totalCustomerCount = totalCustomerCount;
        this.customerName = customerName;
    }

    public Report(String consultantName, String appointmentStart) {
        this.consultantName = consultantName;
        this.appointmentStart = appointmentStart;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public int getTotalCustomerCount() {
        return totalCustomerCount;
    }

    public void setTotalCustomerCount(int totalCustomerCount) {
        this.totalCustomerCount = totalCustomerCount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public static void addConsultantSchedules(Report newItemInReport) {
        consultantSchedules.add(newItemInReport);
    }

    public static void addTotalCustomer(Report newItemInReport) {
        totalCustomers.add(newItemInReport);
    }

}
