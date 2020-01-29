package apptSys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApptSys extends Application {


    public static Stage currStage, prevStage;

    @Override
    public void start(Stage stage) throws Exception {

        currStage = stage;
        prevStage = stage;

        //TODO: get locale and set language & time zone info

        Parent root = FXMLLoader.load(getClass().getResource("view/LogInView.fxml"));
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
