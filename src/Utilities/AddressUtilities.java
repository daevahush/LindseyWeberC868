package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressUtilities {
    static Connection connection = DBConnection.startConnection();

    //Insert new Address into Address table
    public static void insertAddress(String address, String address2, String city, String postalCode) throws SQLException {
        String insertStatement = "INSERT INTO address(address, address2, cityId, postalCode, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,address);
        ps.setString(2,address2);
        ps.setInt(3,CityUtilities.getCityID(city));
        ps.setString(4,postalCode);
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

    //Update Address
    public static void updateAddress(String address, String address2, String city, String postalCode) throws SQLException {
        String updateStatement = "UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, lastUpdateBy = ? " +
                "WHERE addressId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setString(1,address);
        ps.setString(2,address2);
        ps.setInt(3,CityUtilities.getCityID(city));
        ps.setString(4,postalCode);
        ps.setString(5,User.getUserName());
        ps.setInt(6,getAddressID(address, city));

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete Address
    public static void deleteAddress(String address) throws SQLException {
        String deleteStatement = "DELETE FROM address where address = ?;";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, address);

        //Execute Statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get All Addresses
    public static void getAddress() throws SQLException {
        String query = "SELECT * FROM address;";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int addressID = queryResults.getInt("addressId");
            String address = queryResults.getString("address");
            String address2 = queryResults.getString("address2");
            int cityID = queryResults.getInt("cityId");
            String postalCode = queryResults.getString("postalCode");
        }

    }

    //Get Address ID
    public static int getAddressID(String address, String city) throws SQLException {
        String query = "SELECT addressId FROM address WHERE address = ? AND cityId= ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setString(1, address);
        ps.setInt(2, CityUtilities.getCityID(city));

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("addressId");
        }

        return -1;
    }
}
