package apptSys.controller;

import apptSys.ApptSys;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private Tab homeTab, weekTab, monthTab, custTab, repTab;
    @FXML
    private Parent homeView, calWeekView, calMnthView, custListView, reportsView;
    @FXML
    private HomeViewController homeViewController;
    @FXML
    private CalWeekViewController calWeekViewController;
    @FXML
    private CalMnthViewController calMnthViewController;
    @FXML
    private CustListViewController custListViewController;
    @FXML
    private ReportsViewController reportsViewController;


    @FXML
    void homeSelected(Event event) throws MalformedURLException {
        homeViewController.initialize(new URL("file:apptSys/view/HomeView.fxml"), ApptSys.reB);
    }
    @FXML
    void weekSelected(Event event) throws MalformedURLException {
        calWeekViewController.initialize(new URL("file:apptSys/view/CalWeekView.fxml"), ApptSys.reB);
    }
    @FXML
    void monthSelected(Event event) throws MalformedURLException {
        calMnthViewController.initialize(new URL("file:apptSys/view/CalMnthView.fxml"), ApptSys.reB);
    }
    @FXML
    void custSelected(Event event) throws MalformedURLException {
        custListViewController.initialize(new URL("file:apptSys/view/CustListView.fxml"), ApptSys.reB);
    }
    @FXML
    void repSelected(Event event) throws MalformedURLException {
        reportsViewController.initialize(new URL("file:apptSys/view/ReportsView.fxml"), ApptSys.reB);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
