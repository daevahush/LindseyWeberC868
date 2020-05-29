package Utilities;

import Database.DBConnection;
import Database.DBQuery;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentUtilities {
    static Connection connection = DBConnection.startConnection();

    //Get Appointment ID
    public static int getAppointmentID(String patientName, String address, String city, String start) throws SQLException {
        String query = "SELECT appointmentId FROM appointment WHERE patientId = ? && userId = ? && start = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mappings
        ps.setInt(1, PatientUtilities.getPatientID(patientName, address, city));
        ps.setInt(2, SystemUtilities.getUserID(User.getUserName()));
        ps.setString(3, start);

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {

            return queryResults.getInt("appointmentId");
        }

        return -1;
    }


    //Insert new Appointment into Appointment table
    public static void insertAppointment(int patientID, int doctorID, String title, String description, String patientName,
                                         String type, String start, String end) throws SQLException {

        //Convert user provided dates and times to UTC
        String startToUTC = SystemUtilities.toUTC(start);
        String endToUTC = SystemUtilities.toUTC(end);

        String insertStatement = "INSERT INTO appointment(patientId, doctorId, userId, title, description,  " +
                "contact, type, start, end, createDate, createdBy, lastUpdateBy) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setInt(1, patientID);
        ps.setInt(2, doctorID);
        ps.setInt(3, SystemUtilities.getUserID(User.getUserName()));
        ps.setString(4,title);
        ps.setString(5,description);
        ps.setString(6, patientName);
        ps.setString(7, type);
        ps.setString(8,startToUTC);
        ps.setString(9,endToUTC);
        ps.setString(10, SystemUtilities.getSystemDateTime());
        ps.setString(11,User.getUserName());
        ps.setString(12,User.getUserName());

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Update Appointment
    public static void updateAppointment(int patientID, int doctorID, String title, String description, String patientName,
                                         String type, String start, String end, int appointmentID) throws SQLException {

        //Convert user provided dates and times to UTC
        String startToUTC = SystemUtilities.toUTC(start);
        String endToUTC = SystemUtilities.toUTC(end);


        String updateStatement = "UPDATE appointment SET patientId = ?, doctorId = ?, title = ?, description = ?, " +
                "contact = ?, type = ?, start = ?, end = ?, lastUpdateBy = ? WHERE appointmentId = ?;";

        //Create prepared statement
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key value mapping
        ps.setInt(1, patientID);
        ps.setInt(2, doctorID);
        ps.setString(3,title);
        ps.setString(4,description);
        ps.setString(5, patientName);
        ps.setString(6, type);
        ps.setString(7,startToUTC);
        ps.setString(8,endToUTC);
        ps.setString(9,User.getUserName());
        ps.setInt(10, appointmentID);

        //Execute statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Delete Appointment
    public static void deleteAppointment(int appointmentId) throws  SQLException {
        String deleteStatement = "DELETE FROM appointment where appointmentId = ?;";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, appointmentId);

        //Execute Statement
        ps.execute();

        //Confirm rows affected
        if(ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change");
        }
    }

    //Get all Appointments
    public static ObservableList<Appointment> getAllAppointment() throws SQLException {

//        String query = "SELECT appointment.appointmentId, patient.patientId, doctor.doctorId, user.userId, appointment.title, appointment.description, " +
//                "appointment.contact, appointment.type, appointment.start, appointment.end, " +
//                "patient.patientName, doctor.doctorName FROM appointment INNER JOIN patient ON appointment.patientId = patient.patientId " +
//                "INNER JOIN doctor ON appointment.doctorId = doctor.doctorId " +
//                "INNER JOIN user ON appointment.userId = user.userId";

        String query = "SELECT appointment.appointmentId, patient.patientId, doctor.doctorId, user.userId, appointment.title, " +
        "appointment.description, appointment.contact, appointment.type, appointment.start, appointment.end, " +
                "patient.patientName, doctor.doctorName FROM appointment INNER JOIN patient ON appointment.patientId = patient.patientId " +
                "INNER JOIN doctor ON appointment.doctorId = doctor.doctorId " +
                "INNER JOIN user ON appointment.userId = user.userId";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int appointmentID = queryResults.getInt("appointmentId");
            int patientID = queryResults.getInt("patientId");
            String patientName = queryResults.getString("patientName");
            int doctorID = queryResults.getInt("doctorId");
            String doctorName = queryResults.getString("doctorName");
            int userID = queryResults.getInt("userId");
            String title = queryResults.getString("title");
            String description = queryResults.getString("description");
            String contact = queryResults.getString("contact");
            String type = queryResults.getString("type");

            Timestamp dbStart = queryResults.getTimestamp("start");
            Timestamp dbEnd = queryResults.getTimestamp("end");

            String start = SystemUtilities.toUserTime(dbStart);
            String end = SystemUtilities.toUserTime(dbEnd);

            //Add active appointments to list
            Appointment.addAppointment(new Appointment(appointmentID, patientID, patientName, doctorID, doctorName, userID, title,
                    description, contact, type, start, end));
        }

        return Appointment.allAppointments;
    }

    //Get Appointments for current Week
    public static ObservableList<Appointment> getWeekAppointments() throws SQLException {
        LocalDate betweenStart = LocalDate.now();
        LocalDate betweenEnd = LocalDate.now().plusWeeks(1);

        String query = "SELECT appointment.appointmentId, patient.patientId, doctor.doctorId, user.userId, appointment.title, " +
                "appointment.description, appointment.contact, appointment.type, appointment.start, appointment.end, patient.patientName, " +
                "doctor.doctorName FROM appointment INNER JOIN patient ON appointment.patientId = patient.patientId " +
                "INNER JOIN doctor ON appointment.doctorId = doctor.doctorId INNER JOIN user ON appointment.userId = user.userId " +
                "WHERE start >= '" + betweenStart + "' AND start <= '" + betweenEnd + "'";

//        String query = "SELECT * FROM appointment WHERE start >= '" + betweenStart + "' AND start <= '" + betweenEnd + "'";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int appointmentID = queryResults.getInt("appointmentId");
            int patientID = queryResults.getInt("patientId");
            String patientName = queryResults.getString("patientName");
            int doctorID = queryResults.getInt("doctorId");
            String doctorName = queryResults.getString("doctorName");
            int userID = queryResults.getInt("userId");
            String title = queryResults.getString("title");
            String description = queryResults.getString("description");
            String contact = queryResults.getString("contact");
            String type = queryResults.getString("type");

            Timestamp dbStart = queryResults.getTimestamp("start");
            Timestamp dbEnd = queryResults.getTimestamp("end");

            String start = SystemUtilities.toUserTime(dbStart);
            String end = SystemUtilities.toUserTime(dbEnd);

            //Add active appointments to list
            Appointment.addWeeklyAppointment(new Appointment(appointmentID, patientID, patientName, doctorID, doctorName, userID, title,
                    description, contact, type, start, end));
        }

        return Appointment.weeklyAppointments;
    }

    //Get Appointments for current Month
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException {
        LocalDate betweenStart = LocalDate.now();
        LocalDate betweenEnd = LocalDate.now().plusMonths(1);

        String query = "SELECT appointment.appointmentId, patient.patientId, doctor.doctorId, user.userId, appointment.title, " +
                "appointment.description, appointment.contact, appointment.type, appointment.start, appointment.end, " +
                "patient.patientName, doctor.doctorName FROM appointment INNER JOIN patient ON appointment.patientId = patient.patientId " +
                "INNER JOIN doctor ON appointment.doctorId = doctor.doctorId " +
                "INNER JOIN user ON appointment.userId = user.userId " +
                "WHERE start >= '" + betweenStart + "' AND start <= '" + betweenEnd + "'";

//        String query = "SELECT * FROM appointment WHERE start >= '" + betweenStart + "' AND start <= '" + betweenEnd + "'";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            int appointmentID = queryResults.getInt("appointmentId");
            int patientID = queryResults.getInt("patientId");
            String patientName = queryResults.getString("patientName");
            int doctorID = queryResults.getInt("doctorId");
            String doctorName = queryResults.getString("doctorName");
            int userID = queryResults.getInt("userId");
            String title = queryResults.getString("title");
            String description = queryResults.getString("description");
            String contact = queryResults.getString("contact");
            String type = queryResults.getString("type");

            Timestamp dbStart = queryResults.getTimestamp("start");
            Timestamp dbEnd = queryResults.getTimestamp("end");

            String start = SystemUtilities.toUserTime(dbStart);
            String end = SystemUtilities.toUserTime(dbEnd);

            //Add active appointments to list
            Appointment.addMonthlyAppointment(new Appointment(appointmentID, patientID, patientName, doctorID, doctorName, userID, title,
                    description, contact, type, start, end));
        }

        return Appointment.monthlyAppointments;
    }


    //Get Appointments 15 minute Reminder
    public static boolean getAppointmentReminder() throws SQLException {

        String query = "SELECT * FROM appointment WHERE DATEDIFF(SYSDATE(), CAST(start as DATE)) = 0 " +
                "AND TIMEDIFF(CAST(start AS TIME), CURRENT_TIME()) < \"00:15:00\" AND " +
                "TIMEDIFF(CAST(start AS TIME), CURRENT_TIME()) > \"00:00:00\";";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        boolean queryResult = ps.execute();

        //Check if ResultSet is empty
        if(queryResult) {
            return ps.getResultSet().next();
        }
        return false;
    }

    public static ObservableList<Appointment> appointmentTypesByMonth() throws SQLException {
        String query = "SELECT `type`, MONTHNAME(start) as 'Month', COUNT(*) as 'Total' FROM appointment GROUP BY `type`, MONTH(start)";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Execute Statement
        ps.execute();

        //Get ResultSet
        ResultSet queryResults = ps.getResultSet();

        //Retrieve ResultSet values
        while(queryResults.next()) {
            String type = queryResults.getString("type");
            String month = queryResults.getString("Month");
            int total = queryResults.getInt("Total");

            Appointment.addAppointmentTypes(new Appointment(type, month, total));
        }

        return Appointment.appointmentTypes;
    }

    public static boolean withinBusinessHours (String start, String end) throws SQLException {
        //Business hours are between 8:00am EST / 12:00 UTC
        //Ending at 5:00pm EST / 21:00 UTC

        //Change user entered time to UTC
        String utcStart = SystemUtilities.toUTC(start);
        String utcEnd = SystemUtilities.toUTC(end);

        LocalTime startTime = LocalTime.parse(utcStart.substring(11));
        LocalTime endTime = LocalTime.parse(utcEnd.substring(11));

        String open = "12:00:00";
        String closing = "21:00:00";
        LocalTime openTime = LocalTime.parse(open);
        LocalTime closeTime = LocalTime.parse(closing);


        if(startTime.isAfter(openTime) &&startTime.isBefore(closeTime)) {
            return true;
        }

        return false;
    }

    public static boolean overlappingAppointment(String start, String end) throws SQLException {
        //Change user entered time to UTC
        String utcStart = SystemUtilities.toUTC(start);
        String utcEnd = SystemUtilities.toUTC(end);

        String startDate = utcStart.substring(0,10);
        String startTime = utcStart.substring(11);
        String endTime = utcEnd.substring(11);

        String query = "SELECT * FROM appointment WHERE DATEDIFF(?, CAST(start as DATE)) = 0 " +
                "AND CAST(start as TIME) <= ? " +
                " AND CAST(end as TIME) >= ?";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, startDate);
        ps.setString(2, startTime);
        ps.setString(3, endTime);

        boolean queryResult = ps.execute();

        if(queryResult) {
            return ps.getResultSet().next();
        }
        return false;
    }

    public static boolean overlappingUpdate(String newStart, String newEnd, String oldStart) throws SQLException {
        //Change user entered time to UTC
        String utcStart = SystemUtilities.toUTC(newStart);
        String utcEnd = SystemUtilities.toUTC(newEnd);
        String utcOldStart = SystemUtilities.toUTC(oldStart);

        String startDate = utcStart.substring(0,10);
        String startTimeNew = utcStart.substring(11);
        String endTimeNew = utcEnd.substring(11);
        String startTimeOld = utcOldStart.substring(11);

        String query = "SELECT * FROM appointment WHERE DATEDIFF(?, CAST(start as DATE)) = 0 " +
                "AND CAST(start as TIME) >= ? " +
                " AND CAST(end as TIME) <= ? " +
                "AND CAST(start as TIME) != ?";

        DBQuery.setPreparedStatement(connection, query);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, startDate);
        ps.setString(2, startTimeNew);
        ps.setString(3, endTimeNew);
        ps.setString(4, startTimeOld);

        boolean queryResult = ps.execute();

        if(queryResult) {
            return ps.getResultSet().next();
        }
        return false;
    }

}

