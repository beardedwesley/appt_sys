package apptSys.controller;

import apptSys.ApptSys;
import apptSys.model.Appointment;
import apptSys.model.DBAccessory;
import apptSys.model.Logger;
import apptSys.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LogInViewController implements Initializable {

    @FXML
    private Label usernameLbl, passwordLbl;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button logInBtn, cancelBtn;

    private ResourceBundle reB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reB = ApptSys.reB;

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
            //incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }

        Integer userID = apptSys.model.DBAccessory.verifyUser(usernameTxt.getText(), passwordTxt.getText());
        if (userID.equals(null) || userID.intValue() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, reB.getString("noUserMsg"));
            alert.showAndWait();
            return;
        }

        //found, load resources
        ApptSys.currUser = new User(userID, username);

        ApptSys.apptList = DBAccessory.getAppointmentList(userID);
        ApptSys.custList = DBAccessory.getCustList();
        ApptSys.addList = DBAccessory.getAddressList();
        ApptSys.cityList = DBAccessory.getCityList();
        ApptSys.countryList = DBAccessory.getCountryList();

        ApptSys.typeNameList = DBAccessory.getTypeNameList();
        ApptSys.userNameList = DBAccessory.getUserNameList();
        ApptSys.custNameList = DBAccessory.getCustNameList();

        //Log sign in
        Logger.logSignin(ApptSys.currUser);

        //Display main screen
        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        ApptSys.currStage.setScene(new Scene(root));
        ApptSys.currStage.setOnCloseRequest(e -> ApptSys.saveShut());
        ApptSys.currStage.show();

        //check for appointment within next 15 minutes
        LocalDateTime logTime = LocalDateTime.now();
        for (Appointment appt : ApptSys.apptList) {
            if ((appt.getStart().isBefore(logTime.plusMinutes(15)) || appt.getStart().isEqual(logTime.plusMinutes(15)))
                    && (appt.getStart().isAfter(logTime) || appt.getStart().isEqual(logTime))) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You have an Appointment that starts within" +
                        "the next 15 minutes.");
                alert.showAndWait();
                break;
            }
        }

    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        System.exit(0);
    }

}