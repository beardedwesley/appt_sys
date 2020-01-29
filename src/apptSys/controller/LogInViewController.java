package apptSys.controller;

import apptSys.ApptSys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInViewController implements Initializable {

    @FXML
    private Label usernameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button logInBtn;
    @FXML
    private Button cancelBtn;

    private ResourceBundle reB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reB = rb;

        //check user's location and display labels in local language
        usernameLbl.setText(reB.getString("usernameTxt"));
        passwordLbl.setText(reB.getString("passwordTxt"));
        logInBtn.setText(reB.getString("logInBtn"));
        cancelBtn.setText(reB.getString("cancelBtn"));
    }

    @FXML
    void onLogInBtnClick(ActionEvent event) throws Exception {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        //check username and password are not empty
        if(username.isEmpty() || password.isEmpty()){
            //Display error message
            Alert incomplete = new Alert(Alert.AlertType.ERROR, reB.getString("blankMsg"));
            incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }

        //TODO: check username/password combo against database

        //if found, log user's sign-in and load main screen
        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        ApptSys.currStage.setScene(new Scene(root));
        ApptSys.currStage.setOnCloseRequest(e -> System.exit(0));
        ApptSys.currStage.show();

    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        System.exit(0);
    }

}
