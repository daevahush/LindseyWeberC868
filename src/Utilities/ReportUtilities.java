package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.Report;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportUtilities {
    static Connection connection = DBConnection.startConnection();

    public static ObservableList<Report> consultantSchedules() throws SQLException {

        String query = "SELECT user.userName, appointment.start FROM user, appointment GROUP BY appointment.userId, MONTH(start), start";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            String userName = queryResults.getString("userName");
            String start = queryResults.getString("start");

            Report.addConsultantSchedules(new Report(userName, start));

        }

        return Report.consultantSchedules;
    }

    public static ObservableList<Report> getTotalCustomers() throws SQLException {
        String query = "SELECT * FROM customer";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();
        int totalCustomerCount = 0;

        //Retrieve ResultSet values
        while(queryResults.next()) {
            totalCustomerCount++;

            String customerName = queryResults.getString("customerName");

            //Add active appointments to list
            Report.addTotalCustomer(new Report(totalCustomerCount, customerName));
            System.out.println(customerName);
        }

        return Report.totalCustomers;
    }

}
