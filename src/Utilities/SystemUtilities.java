package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import java.io.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

//Utilities to interact with SQL DB
public class SystemUtilities {

    static Connection connection = DBConnection.startConnection();

    //Login Utility to check if user provided username and password values are correct and user is an active user in DB
    public static boolean validateLogIn(String username, String password) throws SQLException {
        Connection connection = DBConnection.startConnection();
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String query = "SELECT userName,password from user where active=1 && userName='" + username
                + "' && password='" + password + "';";

        boolean queryResult = statement.execute(query);

        if(queryResult) {
            return statement.getResultSet().next();
        }
        return false;
    }

    //File name. Located in src packages folder in project files
    private static String fileName = "userLog.txt";

    //Log user login attempts to file
    public static void log(String username, boolean loginSuccessful) {
        try {
            FileWriter file = new FileWriter(fileName, true);
            PrintWriter outputFile = new PrintWriter(file);

            outputFile.print("User " + username +
                    (loginSuccessful ? " successfully logged in at " : " attempted to log in at ") +
                    getSystemDateTime() + "\n");

            outputFile.close();

        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    //Function to get user's userID
    public static int getUserID(String username) throws SQLException {
        Connection connection = DBConnection.startConnection();

        String queryUser = "SELECT userId FROM user WHERE userName = ?";

        DBQuery.setPreparedStatement(connection, queryUser);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, username);

        ResultSet queryResult = ps.executeQuery();

        while(queryResult.next()) {
            return queryResult.getInt("userId");
        }
        return -1;

    }

    //Get current System Time TODO possibly remove
    public static String getSystemDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    //Convert to UTC
    public static String toUTC(String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timestamp ts = Timestamp.valueOf(time);
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        return dtf.format(ldtIn);

    }

    //Convert to user's System timezone
    public static String toUserTime(Timestamp time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime ldtIn = time.toLocalDateTime();
        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of("UTC")); //Change this to the time obtained from the db instead of ldtIn
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();

        return dtf.format(ldtOutFinal);
    }

}
