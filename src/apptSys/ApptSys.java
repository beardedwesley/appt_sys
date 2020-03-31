package apptSys;

import apptSys.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class ApptSys extends Application {

    /* Global Variables */
    public static Stage currStage, prevStage;
    public static ResourceBundle reB;
    public static User currUser;

    public static ObservableList<Appointment> apptList;
    public static ObservableList<Customer> custList;
    public static ObservableList<Address> addList;
    public static ObservableList<City> cityList;
    public static ObservableList<Country> countryList;

    public static ObservableList<String> typeNameList;
    public static ObservableList<String> userNameList;
    public static ObservableList<String> custNameList;


    /* Custom Accessor */
    public static ObservableList<Appointment> getApptListByDay(int daysFromNow){
        ObservableList<Appointment> dayList = FXCollections.observableArrayList();
        LocalDate frame = LocalDateTime.now().toLocalDate().plusDays(daysFromNow);
        for (Appointment appt: apptList) {
            if (appt.getDate().equals(frame)) {
                dayList.add(appt);
            }
        }

        return dayList;
    }

    /* Application Launch */
    public void start(Stage stage) throws Exception {

        currStage = stage;
        prevStage = stage;

        reB = ResourceBundle.getBundle("apptSys.utils.apptSys", new Locale(Locale.getDefault().getLanguage()));

        Parent root = FXMLLoader.load(getClass().getResource("view/LogInView.fxml"));
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    /* Application Close */
    public static void saveShut() {
        Logger.logSignout();
        System.exit(0);
    }
}
