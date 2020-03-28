package apptSys.controller;

import apptSys.ApptSys;
import apptSys.model.Appointment;
import apptSys.model.DBAccessory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportsViewController implements Initializable {

    @FXML
    private ListView<String> typeList, userList, custList;
    @FXML
    private TableView<Appointment> typeApptTbl, userApptTbl, custApptTbl;
    @FXML
    private TableColumn<Appointment, String> typeDateCol, typeTimeCol, typeTitleCol, typeCustCol, userDateCol,
            userTimeCol, userTitleCol, userCustCol, custDateCol, custTimeCol, custTitleCol, custUserCol;

    private void populateTypeTbl (String type) {
        try {
            typeApptTbl.setItems(DBAccessory.getApptListByType(type));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        typeDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCustCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
    }

    private void populateUserTbl (String user) {
        try {
            userApptTbl.setItems(DBAccessory.getApptListByUser(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        userTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        userTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        userCustCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
    }

    private void populateCustTbl (String cust) {
        try {
            custApptTbl.setItems(DBAccessory.getApptListByCust(cust));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        custDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        custTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        custTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        custUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeList.setItems(ApptSys.typeNameList);
        typeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        typeList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateTypeTbl(newValue);
            }
        });

        userList.setItems(ApptSys.userNameList);
        userList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateUserTbl(newValue);
            }
        });

        custList.setItems(ApptSys.custNameList);
        custList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        custList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateCustTbl(newValue);
            }
        });
    }
}



















