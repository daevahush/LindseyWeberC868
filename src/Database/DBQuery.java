package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
    //Statement reference
    private static Statement statement;

    //Create statement object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    //Return statement object
    public static Statement getStatement() {
        return statement;
    }


    //Prepared statement reference
    private static PreparedStatement preparedStatement;

    //Create prepared statement
    public static void setPreparedStatement(Connection connection, String query) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
    }

    //Return prepared statement
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
