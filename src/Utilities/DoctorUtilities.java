package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.Doctor;
import Model.Patient;
import Model.User;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorUtilities {

    static Connection connection = DBConnection.startConnection();

    //Get Doctor ID
    public static int getDoctorID(String customerName, String address, String city) throws SQLException {
        String query = "SELECT doctorId FROM doctor WHERE doctorName = ? && phone = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, customerName);
        ps.setInt(2, AddressUtilities.getAddressID(address, city));

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("customerId");
        }

        return -1;
    }

    //Insert new doctor into doctor table
    public static void insertDoctor(String customerName, int addressID) throws SQLException {
        String insertStatement = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,customerName);
        ps.setInt(2,addressID);
        ps.setInt(3, 1);
        ps.setString(4,SystemUtilities.getSystemDateTime());
        ps.setString(5, User.getUserName());
        ps.setString(6,User.getUserName());

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
    public static void updateDoctor(int customerID, String customerName, int addressID) throws SQLException {
        String updateStatement = "UPDATE customer SET customerName = ?, addressId = ?, lastUpdateBy = ?" +
                "WHERE customerId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,customerName);
        ps.setInt(2,addressID);
        ps.setString(3,User.getUserName());
        ps.setInt(4,customerID);


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
    public static void deleteDoctor(int customerId) throws SQLException {
        String deleteStatement = "DELETE FROM customer WHERE customerId = ?";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setInt(1, customerId);

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get Individual doctor to update
    public static ObservableList<Doctor> getDoctor(int customerId) throws SQLException {
        String query = "SELECT customer.customerId, customer.customerName, " +
                "address.addressId, address.address, address.address2, address.postalCode, address.phone, " +
                "city.cityId, city.city, country.countryId, country.country FROM customer " +
                "INNER JOIN address ON customer.addressId = address.addressId " +
                "INNER JOIN city ON address.cityId = city.cityId " +
                "INNER JOIN country on city.countryId = country.countryId WHERE customerId= " + customerId;

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int customerID = queryResults.getInt("customerId");
            String customerName = queryResults.getString("customerName");
            int addressID = queryResults.getInt("addressId");
            String address = queryResults.getString("address");
            String address2 = queryResults.getString("address2");
            int cityID = queryResults.getInt("cityId");
            String city = queryResults.getString("city");
            int countryID = queryResults.getInt("countryId");
            String country = queryResults.getString("country");
            String postalCode = queryResults.getString("postalCode");
            String phone = queryResults.getString("phone");

            //Add active customers to list
            Patient.addUpdatePatient(new Patient(customerID, customerName, addressID, address, address2,
                    cityID, city, countryID, country, postalCode, phone));
        }

        return Doctor.updateDoctor;
    }

    public static ObservableList<Doctor> getAllDoctors() throws SQLException {
        String query = "SELECT customer.customerId, customer.customerName, " +
                "address.addressId, address.address, address.address2, address.postalCode, address.phone, " +
                "city.cityId, city.city, country.countryId, country.country FROM customer " +
                "INNER JOIN address ON customer.addressId = address.addressId " +
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
            int customerID = queryResults.getInt("customerId");
            String customerName = queryResults.getString("customerName");
            int addressID = queryResults.getInt("addressId");
            String address = queryResults.getString("address");
            String address2 = queryResults.getString("address2");
            int cityID = queryResults.getInt("cityId");
            String city = queryResults.getString("city");
            int countryID = queryResults.getInt("countryId");
            String country = queryResults.getString("country");
            String postalCode = queryResults.getString("postalCode");
            String phone = queryResults.getString("phone");

            //Add active appointments to list
            Patient.addPatient(new Patient(customerID, customerName, addressID, address, address2,
                    cityID, city, countryID, country, postalCode, phone));
        }

        return Doctor.allDoctors;
    }

}

