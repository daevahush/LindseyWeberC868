package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryUtilities {
    static Connection connection = DBConnection.startConnection();

    //Insert new Country into Country table
    public static void insertCountry(String country) throws SQLException {
        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?,?,?,?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,country);
        ps.setString(2, SystemUtilities.getSystemDateTime());
        ps.setString(3, User.getUserName());
        ps.setString(4,User.getUserName());

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }

    }

    //Update Country
    public static void updateCountry(String countryToUpdate, String newCountry) throws SQLException {
        String updateStatement = "UPDATE country SET country = ?, lastUpdateBy = ? WHERE country = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1, newCountry);
        ps.setString(2, User.getUserName());
        ps.setString(3, countryToUpdate);

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete Country
    public static void deleteCountry(String country) throws SQLException {
        String deleteStatement = "DELETE FROM country WHERE country = ?";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1, country);

        //Execute Statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get All Countries
    public static void getCountry() throws SQLException {
        String query = "SELECT * FROM country;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int countryID = queryResults.getInt("countryId");
            String country = queryResults.getString("country");


        }

    }

    //Get Country ID
    public static int getCountryID(String country) throws SQLException {
        String query = "SELECT countryId FROM country WHERE country = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, country);

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("countryId");
        }

        return -1;
    }

}
