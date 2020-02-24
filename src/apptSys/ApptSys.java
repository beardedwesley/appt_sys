package apptSys;

import apptSys.model.Appointment;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class ApptSys extends Application {


    public static Stage currStage, prevStage;
    public static Locale locale;
    public static ResourceBundle reB;
    public static ObservableList<Appointment> apptList;
    public static int userID;

    public void start(Stage stage) throws Exception {

        currStage = stage;
        prevStage = stage;

        //TODO: set time zone info
        locale = Locale.getDefault();
        reB = ResourceBundle.getBundle("ApptSys", locale);

        openLogIn(currStage);
    }

    public void openLogIn(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/LogInView.fxml"));
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    public static void saveShut() {
        //TODO: save any updated data, log details as needed, and safely shutdown application


        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
