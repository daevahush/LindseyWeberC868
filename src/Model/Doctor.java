package Model;

public class Doctor {

    private int doctorID;
    private String doctorName;


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
}
