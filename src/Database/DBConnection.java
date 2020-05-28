package Database;

import com.mysql.jdbc.Connection;
import com.sun.javaws.IconUtil;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql";
    private static final String ipAddress = "://3.227.166.251/U06p23";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver reference
    private static final String mySQLDriver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    //Username
    private static final String username = "U06p23";

    //Password
    private static final String password = "53688832131";

    public static Connection startConnection() {
        try {
            Class.forName(mySQLDriver);
            connection = (Connection) DriverManager.getConnection(jdbcURL, username, password);

        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
