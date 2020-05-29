package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.Doctor;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorUtilities {

    static Connection connection = DBConnection.startConnection();

    //Get Doctor ID
    public static int getDoctorID(String doctorName, String phone) throws SQLException {
        String query = "SELECT doctorId FROM doctor WHERE doctorName = ? && phone = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, doctorName);
        ps.setString(2, phone);

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("doctorId");
        }

        return -1;
    }

    //Insert new doctor into doctor table
    public static void insertDoctor(String doctorName, String phone, String email) throws SQLException {
        String insertStatement = "INSERT INTO doctor(doctorName, phone, email, active, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,doctorName);
        ps.setString(2,phone);
        ps.setString(3,email);
        ps.setInt(4, 1);
        ps.setString(5,SystemUtilities.getSystemDateTime());
        ps.setString(6, User.getUserName());
        ps.setString(7,User.getUserName());

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Update Doctor
    public static void updateDoctor(int doctorID, String doctorName, String phone, String email) throws SQLException {
        String updateStatement = "UPDATE doctor SET doctorName = ?, phone = ?, email = ?, lastUpdateBy = ?" +
                "WHERE doctorId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,doctorName);
        ps.setString(2,phone);
        ps.setString(3,email);
        ps.setString(4,User.getUserName());
        ps.setInt(5,doctorID);


        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete doctor
    public static void deleteDoctor(int doctorID) throws SQLException {
        String deleteStatement = "DELETE FROM doctor WHERE doctorId = ?";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setInt(1, doctorID);

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get Individual doctor to be updated
    public static ObservableList<Doctor> getDoctor(int doctorId) throws SQLException {
        String query = "SELECT doctorId, doctorName, phone, email FROM doctor WHERE doctorId= " + doctorId;

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int doctorID = queryResults.getInt("doctorId");
            String doctorName = queryResults.getString("doctorName");
            String phone = queryResults.getString("phone");
            String email = queryResults.getString("email");

            //Add active customers to list
            Doctor.addUpdateDoctor(new Doctor(doctorID, doctorName, phone, email));
        }

        return Doctor.updateDoctor;
    }

    //Get all existing active doctors
    public static ObservableList<Doctor> getAllDoctors() throws SQLException {
        String query = "SELECT doctorId, doctorName, phone, email FROM doctor;";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int doctorID = queryResults.getInt("doctorId");
            String doctorName = queryResults.getString("doctorName");
            String phone = queryResults.getString("phone");
            String email = queryResults.getString("email");

            //Add active doctors to list
            Doctor.addDoctor(new Doctor(doctorID, doctorName, phone, email));
        }

        return Doctor.allDoctors;
    }

    //Search function for Doctors screen to search by ID
    public static Doctor lookUpDoctor(int doctorID) throws SQLException {
        ObservableList<Doctor> allDoctors = getAllDoctors();

        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor doctor = allDoctors.get(i);

            if (doctor.getDoctorID() == doctorID) {
                return doctor;
            }
        }
        return null;
    }

    //Search doctors by name
    public static ObservableList<Doctor> lookUpDoctor(String doctorName) throws SQLException {
        Doctor.allDoctors.clear();

        ObservableList<Doctor> filteredDoctors = FXCollections.observableArrayList();
        doctorName = doctorName.toLowerCase();

        for (Doctor doctor : getAllDoctors()) {
            String name = doctor.getDoctorName().toLowerCase();

            if (name.contains(doctorName)) {
                filteredDoctors.add(doctor);
            }
        }
        return filteredDoctors;
    }

}

