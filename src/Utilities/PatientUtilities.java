package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.Patient;
import Model.User;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientUtilities {
    static Connection connection = DBConnection.startConnection();

    //Get Patient ID
    public static int getPatientID(String patientName, String address, String city) throws SQLException {
        String query = "SELECT patientId FROM patient WHERE patientName = ? && addressId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, patientName);
        ps.setInt(2, AddressUtilities.getAddressID(address, city));

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("patientId");
        }

        return -1;
    }

    //Insert new patient into patient table
    public static void insertPatient(String patientName, int addressID, String phone, String email) throws SQLException {
        String insertStatement = "INSERT INTO patient(patientName, addressId, phone, email, active, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,patientName);
        ps.setInt(2,addressID);
        ps.setString(3,phone);
        ps.setString(4,email);
        ps.setInt(5, 1);
        ps.setString(6,SystemUtilities.getSystemDateTime());
        ps.setString(7, User.getUserName());
        ps.setString(8,User.getUserName());

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Update patient
    public static void updatePatient(int patientID, String patientName, int addressID) throws SQLException {
        String updateStatement = "UPDATE patient SET patientName = ?, addressId = ?, lastUpdateBy = ?" +
                "WHERE patientId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,patientName);
        ps.setInt(2,addressID);
        ps.setString(3,User.getUserName());
        ps.setInt(4,patientID);


        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete patient
    public static void deletePatient(int patientID) throws SQLException {
        String deleteStatement = "DELETE FROM patient WHERE patientId = ?";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setInt(1, patientID);

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get Individual patient to be updated
    public static ObservableList<Patient> getPatient(int patientId) throws SQLException {
        String query = "SELECT patient.patientId, patient.patientName, patient.phone, patient.email, " +
                "address.addressId, address.address, address.address2, address.postalCode, " +
                "city.cityId, city.city, country.countryId, country.country FROM patient " +
                "INNER JOIN address ON patient.addressId = address.addressId " +
                "INNER JOIN city ON address.cityId = city.cityId " +
                "INNER JOIN country on city.countryId = country.countryId WHERE patientId= " + patientId;

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int patientID = queryResults.getInt("patientId");
            String patientName = queryResults.getString("patientName");
            String phone = queryResults.getString("phone");
            String email = queryResults.getString("email");
            int addressID = queryResults.getInt("addressId");
            String address = queryResults.getString("address");
            String address2 = queryResults.getString("address2");
            int cityID = queryResults.getInt("cityId");
            String city = queryResults.getString("city");
            int countryID = queryResults.getInt("countryId");
            String country = queryResults.getString("country");
            String postalCode = queryResults.getString("postalCode");

            //Add active customers to list
            Patient.addUpdatePatient(new Patient(patientID, patientName, addressID, phone, email, address, address2,
                    cityID, city, countryID, country, postalCode));
        }

        return Patient.updatePatient;
    }

    public static ObservableList<Patient> getAllPatients() throws SQLException {
        String query = "SELECT patient.patientId, patient.patientName, patient.phone, patient.email,  " +
                "address.addressId, address.address, address.address2, address.postalCode, " +
                "city.cityId, city.city, country.countryId, country.country FROM patient " +
                "INNER JOIN address ON patient.addressId = address.addressId " +
                "INNER JOIN city ON address.cityId = city.cityId " +
                "INNER JOIN country on city.countryId = country.countryId ;";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int patientID = queryResults.getInt("patientId");
            String patientName = queryResults.getString("patientName");
            int addressID = queryResults.getInt("addressId");
            String phone = queryResults.getString("phone");
            String email = queryResults.getString("email");
            String address = queryResults.getString("address");
            String address2 = queryResults.getString("address2");
            int cityID = queryResults.getInt("cityId");
            String city = queryResults.getString("city");
            int countryID = queryResults.getInt("countryId");
            String country = queryResults.getString("country");
            String postalCode = queryResults.getString("postalCode");

            //Add active appointments to list
            Patient.addPatient(new Patient(patientID, patientName, addressID, phone, email, address, address2,
                    cityID, city, countryID, country, postalCode));
        }

        return Patient.allPatients;
    }

}
