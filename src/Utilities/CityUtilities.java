package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityUtilities {
    static Connection connection = DBConnection.startConnection();

    //Insert new City into City table
    public static void insertCity(String city, String country) throws SQLException {

        String insertStatement = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,city);
        ps.setInt(2,CountryUtilities.getCountryID(country));
        ps.setString(3, SystemUtilities.getSystemDateTime());
        ps.setString(4, User.getUserName());
        ps.setString(5,User.getUserName());

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Update City
    public static void updateCity(String cityToUpdate, String newCity) throws SQLException {
        String updateStatement = "UPDATE city SET city = ?, lastUpdateBy = ? WHERE city = ?;" ;

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, newCity);
        ps.setString(2, User.getUserName());
        ps.setString(3, cityToUpdate);

        //Execute Statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete City
    public static void deleteCity(String city) throws SQLException {
        String deleteStatement = "DELETE FROM city where city = ?;";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, city);

        //Execute Statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get All Cities
    public static void getCity() throws SQLException {
        String query = "SELECT * FROM city;";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int cityID = queryResults.getInt("cityId");
            String city = queryResults.getString("city");
            int countryID = queryResults.getInt("countryId");

        }

    }

    //Get City ID
    public static int getCityID(String city) throws SQLException {
        String query = "SELECT cityId FROM city WHERE city = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, city);

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("cityId");
        }

        return -1;
    }

}
