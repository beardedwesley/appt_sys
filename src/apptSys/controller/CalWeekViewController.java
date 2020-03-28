package apptSys.controller;

import apptSys.ApptSys;
import apptSys.model.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CalWeekViewController implements Initializable {

    @FXML
    private TableView<Appointment> day1Tbl, day2Tbl, day3Tbl, day4Tbl, day5Tbl, day6Tbl, day7Tbl;

    @FXML
    private TableColumn<Appointment, String> day1Col, day2Col, day3Col, day4Col, day5Col, day6Col, day7Col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        day1Tbl.setItems(ApptSys.getApptListByDay(0));
        day1Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day1Col.setText(LocalDateTime.now().toLocalDate().getDayOfWeek().toString());

        day2Tbl.setItems(ApptSys.getApptListByDay(1));
        day2Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day2Col.setText(LocalDateTime.now().toLocalDate().plusDays(1).getDayOfWeek().toString());

        day3Tbl.setItems(ApptSys.getApptListByDay(2));
        day3Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day3Col.setText(LocalDateTime.now().toLocalDate().plusDays(2).getDayOfWeek().toString());

        day4Tbl.setItems(ApptSys.getApptListByDay(3));
        day4Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day4Col.setText(LocalDateTime.now().toLocalDate().plusDays(3).getDayOfWeek().toString());

        day5Tbl.setItems(ApptSys.getApptListByDay(4));
        day5Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day5Col.setText(LocalDateTime.now().toLocalDate().plusDays(4).getDayOfWeek().toString());

        day6Tbl.setItems(ApptSys.getApptListByDay(5));
        day6Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day6Col.setText(LocalDateTime.now().toLocalDate().plusDays(5).getDayOfWeek().toString());

        day7Tbl.setItems(ApptSys.getApptListByDay(6));
        day7Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day7Col.setText(LocalDateTime.now().toLocalDate().plusDays(6).getDayOfWeek().toString());

    }
}
