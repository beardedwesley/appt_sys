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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    /* Instance Variables */
    private Appointment selected;
    private boolean flagIsNew = false;
    private ObservableList<LocalTime> apptStart = FXCollections.observableArrayList();
    private ObservableList<LocalTime> apptEnd = FXCollections.observableArrayList();
    private ArrayList<Appointment> dayApptList = new ArrayList<Appointment>();

    @FXML
    private TextField apptTitleTxt, apptTypeTxt, apptContactTxt;
    @FXML
    private DatePicker apptStartDatePkr;
    @FXML
    private ComboBox<LocalTime> apptStartTimeOpt, apptEndTimeOpt;
    @FXML
    private TextArea apptDescTxt, apptLocTxt;
    @FXML
    private ComboBox<Customer> apptCustOpt;

    @FXML
    private TableView<Appointment> agendaTbl;
    @FXML
    private TableColumn<Appointment, LocalDate> apptDateCol;
    @FXML
    private TableColumn<Appointment, LocalTime> apptTimeCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol, apptCustCol;

    /* Action-Triggered Methods */
    @FXML
    void apptNewBtnClk(ActionEvent event) {
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void apptDelBtnClk(ActionEvent event) {

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
                    agendaTbl.getSelectionModel().select(1);//selected = null;
                } catch (SQLException e) {
                    Alert oops = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please make" +
                            " sure you are connected to the internet and try again.");
                    oops.showAndWait();
                }
            }
        });


    }
    @FXML
    void apptSaveBtnClk(ActionEvent event) {
        int index = ApptSys.apptList.indexOf(selected);
        //make sure field are filled
        if (apptTitleTxt.getText().isEmpty() || apptTypeTxt.getText().isEmpty() || apptStartDatePkr.getValue() == null
            || apptStartTimeOpt.getValue() == null || apptEndTimeOpt.getValue() == null || apptDescTxt.getText().isEmpty()
            || apptLocTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields except Contact must have a value.");
            alert.showAndWait();
            return;
        }

        Appointment conflict;
        try{
            conflict = checkCustAvail();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database is currently unavailable. Please " +
                    "check your internet connection and try again.");
            alert.showAndWait();
            return;
        }
        if (!(conflict == null)) {
            StringBuilder errMessage = new StringBuilder();
            errMessage.append(apptCustOpt.getValue().getCustomerName()).append(" has an appointment from ");
            errMessage.append(conflict.getStartTime().truncatedTo(ChronoUnit.MINUTES).toString()).append(" to ");
            errMessage.append(conflict.getEndTime().truncatedTo(ChronoUnit.MINUTES).toString()).append(".\n");
            errMessage.append("Please update your appointment to outside of these times.");
            Alert custConflict = new Alert(Alert.AlertType.ERROR, errMessage.toString());
            custConflict.showAndWait();
            return;
        }

        //character limit enforcement
        if (apptTitleTxt.getText().length() > 255) {
            apptTitleTxt.setText(apptTitleTxt.getText().substring(0, 255));
        }

        //adding new appointment
        if (flagIsNew){
            int newID;
            Appointment newAppt;
            try {
                newID = DBAccessory.getNextApptID();
                newAppt = new Appointment(
                        newID,
                        apptCustOpt.getValue().getCustomerID(),
                        ApptSys.currUser.getUserID(),
                        apptTitleTxt.getText(),
                        apptDescTxt.getText(),
                        apptLocTxt.getText(),
                        apptContactTxt.getText(),
                        apptTypeTxt.getText(),
                        "",
                        LocalDateTime.of(apptStartDatePkr.getValue(), apptStartTimeOpt.getValue()),
                        LocalDateTime.of(apptStartDatePkr.getValue(), apptEndTimeOpt.getValue()),
                        ApptSys.currUser.getUserName());
                DBAccessory.addAppt(newAppt);
                ApptSys.apptList.add(newAppt);
                if (!(ApptSys.typeNameList.contains(newAppt.getType()))){
                    ApptSys.typeNameList.add(newAppt.getType());
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database is currently unavailable. Please " +
                        "check your internet connection and try again.");
                alert.showAndWait();
                return;
            }
        } else {
            try {
                selected.updateAppt(
                        apptCustOpt.getValue().getCustomerID(),
                        apptTitleTxt.getText(),
                        apptDescTxt.getText(),
                        apptLocTxt.getText(),
                        apptContactTxt.getText(),
                        apptTypeTxt.getText(),
                        LocalDateTime.of(apptStartDatePkr.getValue(), apptStartTimeOpt.getValue()),
                        LocalDateTime.of(apptStartDatePkr.getValue(), apptEndTimeOpt.getValue())
                );
                DBAccessory.updateAppt(selected);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database is currently unavailable. Please " +
                        "check your internet connection and try again.");
                alert.showAndWait();
            } finally {
                ApptSys.apptList.set(index, selected);
            }
        }
        flagIsNew = false;
    }
    @FXML
    void apptCanxBtnClk(ActionEvent event) {

        fillAllFields(selected);
        flagIsNew = false;
    }
    @FXML
    void setStartAvailability(ActionEvent event) {
        apptStart.clear();
        apptEnd.clear();
        dayApptList = new ArrayList<Appointment>();
        for (Appointment app : ApptSys.apptList) {
            if (app.getDate().isEqual(apptStartDatePkr.getValue())) {
                dayApptList.add(app);
            }
        }

        LocalTime busEnd = LocalTime.of(17, 0);
        LocalTime busCurr = LocalTime.of(8, 0);

        for (Appointment appt : dayApptList) {
            if (appt.equals(selected)) {
                for (;busCurr.isBefore(appt.getEndTime()); busCurr = busCurr.plusMinutes(15)) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    apptStart.add(avail);
                }
                continue;
            }

            for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                if (busCurr.isBefore(appt.getStartTime())) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    apptStart.add(avail);
                } else if (busCurr.equals(appt.getStartTime()) ||
                        (busCurr.isAfter(appt.getStartTime()) && busCurr.isBefore(appt.getEndTime()))) {
                    continue;
                } else if (busCurr.equals(appt.getEndTime())) {
                    break;
                }
            }
        }

        if (busCurr.isBefore(busEnd)) {
            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                apptStart.add(avail);
            }
        }

    }
    @FXML
    void setEndAvailability(ActionEvent event) {
        if (apptStart.isEmpty() || apptStartTimeOpt.getValue() == null) {return;}

        LocalTime busEnd = LocalTime.of(17, 1);
        LocalTime busCurr = apptStartTimeOpt.getValue();

        if (dayApptList.isEmpty() || dayApptList.size() == 1) {
            busCurr = busCurr.plusMinutes(15);
            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                apptEnd.add(avail);
            }
        } else {
            for (Appointment appt : dayApptList) {
                if (dayApptList.indexOf(appt) == (dayApptList.size() - 1)) {
                    busCurr = busCurr.plusMinutes(15);
                    for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                        LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                        apptEnd.add(avail);
                    }
                } else {
                    for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                        if (busCurr.isBefore(appt.getStartTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            apptEnd.add(avail);
                        } else if (busCurr.equals(appt.getStartTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            apptEnd.add(avail);
                            return;
                        } else if (busCurr.isAfter(appt.getStartTime())) {
                            return;
                        }
                    }
                }
            }
        }
    }
    @FXML
    void apptFillLocation(ActionEvent event) {
        if (apptCustOpt.getValue() == null) {return;}
        if (!(apptLocTxt.getText().equalsIgnoreCase(apptCustOpt.getValue().getAddress().toString()))){
            apptLocTxt.setText(apptCustOpt.getValue().getAddress().toString());
        }
    }

    /* Helper Methods */
    private void clearAllFields() {
        apptTitleTxt.setText("");
        apptStartDatePkr.setValue(LocalDate.now());
        apptStartTimeOpt.getSelectionModel().clearSelection();
        apptTypeTxt.setText("");
        apptContactTxt.setText("");
        apptEndTimeOpt.getSelectionModel().clearSelection();
        apptDescTxt.setText("");
        apptCustOpt.getSelectionModel().clearSelection();
        apptLocTxt.setText("");
        apptStart.clear();
        apptEnd.clear();
    }
    private void fillAllFields(Appointment appt) {
        apptTitleTxt.setText(appt.getTitle());
        apptStartDatePkr.setValue(appt.getStart().toLocalDate());
        apptStartTimeOpt.setValue(appt.getStart().toLocalTime());
        apptTypeTxt.setText(appt.getType());
        apptContactTxt.setText(appt.getContact());
        apptEndTimeOpt.setValue(appt.getEnd().toLocalTime());
        apptDescTxt.setText(appt.getDescription());
        apptCustOpt.getSelectionModel().select(appt.getCustomer());
        apptLocTxt.setText(appt.getLocation());
    }
    private Appointment checkCustAvail() throws SQLException {
        Appointment conflict = null;
        Customer currCust = apptCustOpt.getValue();
        ObservableList<Appointment> custApptList = DBAccessory.getApptListByCust(currCust.getCustomerName());
        for (Appointment currAppt : custApptList) {
            if (currAppt.getStart().toLocalDate().isEqual(apptStartDatePkr.getValue())) {
                if ((!(currAppt.getStartTime().isBefore(apptStartTimeOpt.getValue()) && (currAppt.getEndTime().isBefore(apptStartTimeOpt.getValue()) || currAppt.getEndTime().equals(apptStartTimeOpt.getValue()))))
                    && (!((currAppt.getStartTime().equals(apptStartTimeOpt.getValue()) || currAppt.getStartTime().isAfter(apptEndTimeOpt.getValue())) && currAppt.getEndTime().isAfter(apptEndTimeOpt.getValue())))) {
                        conflict = currAppt;
                }
            }
        }
        return conflict;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TableView set up
        agendaTbl.setItems(ApptSys.apptList);
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        apptTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptCustCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        agendaTbl.selectionModelProperty().get().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selected = newValue;
                this.fillAllFields(selected);
            }
        });

        //Set resources for option boxes and event/selection listeners
        try {
            apptCustOpt.setItems(DBAccessory.getCustList());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please check your internet" +
                    " connection and restart the application.");
            alert.showAndWait();
            ApptSys.saveShut();
        }
        apptCustOpt.setVisibleRowCount(7);
        apptStartTimeOpt.setItems(apptStart);
        apptStartTimeOpt.setVisibleRowCount(7);
        apptEndTimeOpt.setItems(apptEnd);
        apptEndTimeOpt.setVisibleRowCount(7);

        //Select first appointment to pre-fill
        agendaTbl.getSelectionModel().select(1);
    }
}

























