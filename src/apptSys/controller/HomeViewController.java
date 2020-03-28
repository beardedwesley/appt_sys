package apptSys.controller;

import apptSys.ApptSys;
import apptSys.model.Appointment;
import apptSys.model.Customer;
import apptSys.model.DBAccessory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    /* Instance Variables */
    private Appointment selected;
    private boolean flagIsNew = false;
    private ObservableList<LocalTime> start = FXCollections.observableArrayList();
    private ObservableList<LocalTime> end = FXCollections.observableArrayList();
    private ArrayList<Appointment> dayApptList = new ArrayList<Appointment>();

    @FXML
    private TextField titleTxt, apptTypeTxt;
    @FXML
    private DatePicker startDatePkr;
    @FXML
    private ChoiceBox<LocalTime> startTimeOpt, endTimeOpt;
    @FXML
    private TextArea descTxt, locTxt;
    @FXML
    private ChoiceBox<Customer> custOpt;

    @FXML
    private TableView<Appointment> agendaTbl;
    @FXML
    private TableColumn<Appointment, LocalDate> dateCol;
    @FXML
    private TableColumn<Appointment, LocalTime> timeCol;
    @FXML
    private TableColumn<Appointment, String> titleCol, custCol;

    /* Action-Triggered Methods */
    @FXML
    void newBtnClk(ActionEvent event) {
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void delBtnClk(ActionEvent event) {

        if (flagIsNew) {
            fillAllFields(selected);
            flagIsNew = false;
            return;
        }
        //confirm user wants to delete appointment then do it
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                " appointment? This cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    DBAccessory.deleteAppointment(selected);
                    ApptSys.apptList.remove(selected);
                    selected = null;
                } catch (SQLException e) {
                    Alert oops = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please make" +
                            " sure you are connected to the internet and try again.");
                    oops.showAndWait();
                }
            }
        });


    }
    @FXML
    void saveBtnClk(ActionEvent event) throws SQLException {

        //make sure field are filled
        if (titleTxt.getText().isEmpty() || apptTypeTxt.getText().isEmpty() || startDatePkr.getValue() == null
            || startTimeOpt.getValue() == null || endTimeOpt.getValue() == null || descTxt.getText().isEmpty()
            || locTxt.getText().isEmpty() || custOpt.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must have a value.");
            alert.showAndWait();
            return;
        }

        //character limit enforcement
        if (titleTxt.getText().length() > 255) {
            titleTxt.setText(titleTxt.getText().substring(0, 255));
        }

        //adding new appointment
        if (flagIsNew){
            Appointment newAppt = new Appointment(
                    DBAccessory.getNextApptID(),
                    custOpt.getSelectionModel().getSelectedItem().getCustomerID(),
                    ApptSys.currUser.getUserID(),
                    titleTxt.getText(),
                    descTxt.getText(),
                    locTxt.getText(),
                    custOpt.getSelectionModel().getSelectedItem().getCustomerName(),
                    apptTypeTxt.getText(),
                    "",
                    ApptSys.currUser.getUserName()
            );

            ApptSys.apptList.add(newAppt);
            DBAccessory.addApt(newAppt);
            return;
        } else {
            selected.updateAppt(
                    selected.getCustomerID(),
                    titleTxt.getText(),
                    descTxt.getText(),
                    locTxt.getText(),
                    custOpt.getValue().getCustomerName(),
                    apptTypeTxt.getText(),
                    LocalDateTime.of(startDatePkr.getValue(), startTimeOpt.getValue()),
                    LocalDateTime.of(startDatePkr.getValue(), endTimeOpt.getValue())
            );
            DBAccessory.updateAppt(selected);
        }

        flagIsNew = false;
    }
    @FXML
    void canxBtnClk(ActionEvent event) {

        fillAllFields(selected);
        flagIsNew = false;
    }
    @FXML
    void setStartAvailability(ActionEvent event) {
        start = start.sorted();
        dayApptList = new ArrayList<Appointment>();
        for (Appointment app : ApptSys.apptList) {
            if (app.getDate().isEqual(startDatePkr.getValue())) {
                dayApptList.add(app);
            }
        }

        LocalTime busEnd = LocalTime.of(17, 0);
        LocalTime busCurr = LocalTime.of(8, 0);

        for (Appointment appt : dayApptList) {

            for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                if (busCurr.isBefore(appt.getStart().toLocalTime())) {
                    start.add(busCurr);
                } else if (busCurr.equals(appt.getStartTime()) ||
                        (busCurr.isAfter(appt.getStartTime()) && busCurr.isBefore(appt.getEndTime()))) {
                    continue;
                } else if (busCurr.equals(appt.getEnd().toLocalTime())) {
                    break;
                }
            }
        }

        if (busCurr.isBefore(busEnd)) {
            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                start.add(busCurr);
            }
        }

    }
    void setEndAvailability() {
        end = end.sorted();
        LocalTime busEnd = LocalTime.of(17, 0);
        LocalTime busCurr = startTimeOpt.getValue().plusMinutes(15);

        for (Appointment appt : dayApptList) {

            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                if (busCurr.isBefore(appt.getStartTime())) {
                    end.add(busCurr);
                } else if (busCurr.equals(appt.getStartTime())) {
                    end.add(busCurr);
                    return;
                } else if (busCurr.isAfter(appt.getStartTime())){
                    break;
                }
            }
        }
    }

    /* Helper Methods */
    private void clearAllFields() {
        titleTxt.clear();
        startDatePkr.getEditor().clear();
        startTimeOpt.getSelectionModel().select(0);
        apptTypeTxt.clear();
        endTimeOpt.getSelectionModel().select(0);
        descTxt.clear();
        custOpt.getSelectionModel().selectFirst();
        locTxt.clear();
    }
    private void fillAllFields(Appointment appt) {
        titleTxt.setText(appt.getTitle());
        startDatePkr.setValue(appt.getStart().toLocalDate());
        startTimeOpt.setValue(appt.getStartTime());
        apptTypeTxt.setText(appt.getType());
        endTimeOpt.setValue(appt.getEndTime());
        descTxt.setText(appt.getDescription());
        custOpt.setValue(appt.getCustomer());
        locTxt.setText(appt.getLocation());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TableView set up
        agendaTbl.setItems(ApptSys.apptList);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        agendaTbl.selectionModelProperty().get().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillAllFields(newValue);
                selected = newValue;}
        });

        //Set resources for option boxes and event/selection listeners
        try {
            custOpt.setItems(DBAccessory.getCustList());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please check your internet" +
                    " connection and restart the application.");
            alert.showAndWait();
            ApptSys.saveShut();
        }
        custOpt.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                locTxt.setText(newValue.getSelectedItem().getAddress().toString());
            }
        }); //This lamda allows me to set an event listener and consumer in a single statement instead of needing to define a helper method
        startTimeOpt.setItems(start);
        endTimeOpt.setItems(end);
        startTimeOpt.selectionModelProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setEndAvailability();
            }
        }));

        //Select first appointment to pre-fill
        agendaTbl.selectionModelProperty().get().selectFirst();
    }
}

























