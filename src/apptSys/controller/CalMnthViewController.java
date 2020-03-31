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

public class CalMnthViewController implements Initializable {

    @FXML
    private TableView<Appointment> day00Tbl, day01Tbl, day02Tbl, day03Tbl, day04Tbl, day05Tbl, day06Tbl, day10Tbl,
            day11Tbl, day12Tbl, day13Tbl, day14Tbl, day15Tbl, day16Tbl, day20Tbl, day21Tbl, day22Tbl, day23Tbl,
            day24Tbl, day25Tbl, day26Tbl, day30Tbl, day31Tbl, day32Tbl, day33Tbl, day34Tbl, day35Tbl, day36Tbl,
            day40Tbl, day41Tbl, day42Tbl, day43Tbl, day44Tbl, day45Tbl, day46Tbl;

    @FXML
    private TableColumn<Appointment, String> day00Col, day01Col, day02Col, day03Col, day04Col, day05Col, day06Col,
            day10Col, day11Col, day12Col, day13Col, day14Col, day15Col, day16Col, day20Col, day21Col, day22Col,
            day23Col, day24Col, day25Col, day26Col, day30Col, day31Col, day32Col, day33Col, day34Col, day35Col,
            day36Col, day40Col, day41Col, day42Col, day43Col, day44Col, day45Col, day46Col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        day00Tbl.setItems(ApptSys.getApptListByDay(0));
        day00Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day00Col.setText(((Integer) LocalDateTime.now().toLocalDate().getDayOfMonth()).toString());

        day01Tbl.setItems(ApptSys.getApptListByDay(1));
        day01Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day01Col.setText(((Integer) LocalDateTime.now().plusDays(1).toLocalDate().getDayOfMonth()).toString());

        day02Tbl.setItems(ApptSys.getApptListByDay(2));
        day02Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day02Col.setText(((Integer) LocalDateTime.now().plusDays(2).toLocalDate().getDayOfMonth()).toString());

        day03Tbl.setItems(ApptSys.getApptListByDay(3));
        day03Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day03Col.setText(((Integer) LocalDateTime.now().plusDays(3).toLocalDate().getDayOfMonth()).toString());

        day04Tbl.setItems(ApptSys.getApptListByDay(4));
        day04Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day04Col.setText(((Integer) LocalDateTime.now().plusDays(4).toLocalDate().getDayOfMonth()).toString());

        day05Tbl.setItems(ApptSys.getApptListByDay(5));
        day05Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day05Col.setText(((Integer) LocalDateTime.now().plusDays(5).toLocalDate().getDayOfMonth()).toString());

        day06Tbl.setItems(ApptSys.getApptListByDay(6));
        day06Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day06Col.setText(((Integer) LocalDateTime.now().plusDays(6).toLocalDate().getDayOfMonth()).toString());

        day10Tbl.setItems(ApptSys.getApptListByDay(7));
        day10Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day10Col.setText(((Integer) LocalDateTime.now().plusDays(7).toLocalDate().getDayOfMonth()).toString());

        day11Tbl.setItems(ApptSys.getApptListByDay(8));
        day11Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day11Col.setText(((Integer) LocalDateTime.now().plusDays(8).toLocalDate().getDayOfMonth()).toString());

        day12Tbl.setItems(ApptSys.getApptListByDay(9));
        day12Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day12Col.setText(((Integer) LocalDateTime.now().plusDays(9).toLocalDate().getDayOfMonth()).toString());

        day13Tbl.setItems(ApptSys.getApptListByDay(10));
        day13Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day13Col.setText(((Integer) LocalDateTime.now().plusDays(10).toLocalDate().getDayOfMonth()).toString());

        day14Tbl.setItems(ApptSys.getApptListByDay(11));
        day14Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day14Col.setText(((Integer) LocalDateTime.now().plusDays(11).toLocalDate().getDayOfMonth()).toString());

        day15Tbl.setItems(ApptSys.getApptListByDay(12));
        day15Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day15Col.setText(((Integer) LocalDateTime.now().plusDays(12).toLocalDate().getDayOfMonth()).toString());

        day16Tbl.setItems(ApptSys.getApptListByDay(13));
        day16Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day16Col.setText(((Integer) LocalDateTime.now().plusDays(13).toLocalDate().getDayOfMonth()).toString());

        day20Tbl.setItems(ApptSys.getApptListByDay(14));
        day20Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day20Col.setText(((Integer) LocalDateTime.now().plusDays(14).toLocalDate().getDayOfMonth()).toString());

        day21Tbl.setItems(ApptSys.getApptListByDay(15));
        day21Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day21Col.setText(((Integer) LocalDateTime.now().plusDays(15).toLocalDate().getDayOfMonth()).toString());

        day22Tbl.setItems(ApptSys.getApptListByDay(16));
        day22Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day22Col.setText(((Integer) LocalDateTime.now().plusDays(16).toLocalDate().getDayOfMonth()).toString());

        day23Tbl.setItems(ApptSys.getApptListByDay(17));
        day23Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day23Col.setText(((Integer) LocalDateTime.now().plusDays(17).toLocalDate().getDayOfMonth()).toString());

        day24Tbl.setItems(ApptSys.getApptListByDay(18));
        day24Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day24Col.setText(((Integer) LocalDateTime.now().plusDays(18).toLocalDate().getDayOfMonth()).toString());

        day25Tbl.setItems(ApptSys.getApptListByDay(19));
        day25Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day25Col.setText(((Integer) LocalDateTime.now().plusDays(19).toLocalDate().getDayOfMonth()).toString());

        day26Tbl.setItems(ApptSys.getApptListByDay(20));
        day26Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day26Col.setText(((Integer) LocalDateTime.now().plusDays(20).toLocalDate().getDayOfMonth()).toString());

        day30Tbl.setItems(ApptSys.getApptListByDay(21));
        day30Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day30Col.setText(((Integer) LocalDateTime.now().plusDays(21).toLocalDate().getDayOfMonth()).toString());

        day31Tbl.setItems(ApptSys.getApptListByDay(22));
        day31Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day31Col.setText(((Integer) LocalDateTime.now().plusDays(22).toLocalDate().getDayOfMonth()).toString());

        day32Tbl.setItems(ApptSys.getApptListByDay(23));
        day32Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day32Col.setText(((Integer) LocalDateTime.now().plusDays(23).toLocalDate().getDayOfMonth()).toString());

        day33Tbl.setItems(ApptSys.getApptListByDay(24));
        day33Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day33Col.setText(((Integer) LocalDateTime.now().plusDays(24).toLocalDate().getDayOfMonth()).toString());

        day34Tbl.setItems(ApptSys.getApptListByDay(25));
        day34Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day34Col.setText(((Integer) LocalDateTime.now().plusDays(25).toLocalDate().getDayOfMonth()).toString());

        day35Tbl.setItems(ApptSys.getApptListByDay(26));
        day35Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day35Col.setText(((Integer) LocalDateTime.now().plusDays(26).toLocalDate().getDayOfMonth()).toString());

        day36Tbl.setItems(ApptSys.getApptListByDay(27));
        day36Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day36Col.setText(((Integer) LocalDateTime.now().plusDays(27).toLocalDate().getDayOfMonth()).toString());

        day40Tbl.setItems(ApptSys.getApptListByDay(28));
        day40Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day40Col.setText(((Integer) LocalDateTime.now().plusDays(28).toLocalDate().getDayOfMonth()).toString());

        day41Tbl.setItems(ApptSys.getApptListByDay(29));
        day41Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day41Col.setText(((Integer) LocalDateTime.now().plusDays(29).toLocalDate().getDayOfMonth()).toString());

        day42Tbl.setItems(ApptSys.getApptListByDay(30));
        day42Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day42Col.setText(((Integer) LocalDateTime.now().plusDays(30).toLocalDate().getDayOfMonth()).toString());

        day43Tbl.setItems(ApptSys.getApptListByDay(31));
        day43Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day43Col.setText(((Integer) LocalDateTime.now().plusDays(31).toLocalDate().getDayOfMonth()).toString());

        day44Tbl.setItems(ApptSys.getApptListByDay(32));
        day44Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day44Col.setText(((Integer) LocalDateTime.now().plusDays(32).toLocalDate().getDayOfMonth()).toString());

        day45Tbl.setItems(ApptSys.getApptListByDay(33));
        day45Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day45Col.setText(((Integer) LocalDateTime.now().plusDays(33).toLocalDate().getDayOfMonth()).toString());

        day46Tbl.setItems(ApptSys.getApptListByDay(34));
        day46Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day46Col.setText(((Integer) LocalDateTime.now().plusDays(34).toLocalDate().getDayOfMonth()).toString());
    }
}