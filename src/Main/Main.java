package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        //        primaryStage.getStyleSheets().add("/CSS/css.css");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setTitle("Charlie Foxtrot Appointment Scheduler");
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        //Establish connection to DB
        DBConnection.startConnection();

        launch(args);

        //Disconnect from DB
        DBConnection.closeConnection();
    }

}
