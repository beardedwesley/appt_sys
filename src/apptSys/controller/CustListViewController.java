package apptSys.controller;

import apptSys.ApptSys;
import apptSys.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CustListViewController implements Initializable {

    /* Instance Variables */
    private TableView.TableViewSelectionModel<Customer> custSelectModel;
    private Customer selected;
    private boolean flagIsNew = false;

    @FXML
    private TextField custNameTxt, custPhoneTxt, custAdd1Txt, custAdd2Txt, custCityTxt, custPCTxt;
    @FXML
    private ChoiceBox<String> custCountryOpt;
    @FXML
    private TableView<Customer> custListTbl;
    @FXML
    private TableColumn<Customer, String> custNameCol, custPhoneCol, custCityCol, custCountryCol;

    @FXML
    void onNewBtn(ActionEvent event) {
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void onDelBtn(ActionEvent event) {
        if (flagIsNew) {
            fillAllFields(selected);
            flagIsNew = false;
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                    " customer? All appointments with this customer will be removed. This cannot be undone.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        DBAccessory.deleteCustomer(selected);
                        ApptSys.custList.remove(selected);
                        ApptSys.custNameList.remove(selected.getCustomerName());
                        custSelectModel.select(1);
                    } catch (SQLException e) {
                        Alert oops = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please make" +
                                " sure you are connected to the internet and try again.");
                        oops.showAndWait();
                    }
                }
            });
        }
    }
    @FXML
    void onSaveBtn(ActionEvent event) throws SQLException {

        //make sure field are filled
        if (custNameTxt.getText().isEmpty() || custPhoneTxt.getText().isEmpty() || custAdd1Txt.getText().isEmpty()
                || custAdd2Txt.getText().isEmpty() || custCityTxt.getText().isEmpty() || custPCTxt.getText().isEmpty()
                || custCountryOpt.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must have a value.");
            alert.showAndWait();
            return;
        }

        //character limit enforcement
        if (custNameTxt.getText().length() > 45) {
            custNameTxt.setText(custNameTxt.getText().substring(0, 45));
        }

        if (flagIsNew) {
            Country country = new Country(
                    DBAccessory.getNextCountryID(),
                    custCountryOpt.getValue(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
            );

            DBAccessory.addCountry(country);

            City citE = new City(
                    DBAccessory.getNextCityID(),
                    custCityTxt.getText(),
                    country.getCountryID(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
            );

            DBAccessory.addCity(citE);

            Address addy = new Address(
                    DBAccessory.getNextAddID(),
                    custAdd1Txt.getText(),
                    custAdd2Txt.getText(),
                    citE.getCityID(),
                    custPCTxt.getText(),
                    custPhoneTxt.getText(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
            );

            DBAccessory.addAddress(addy);

            Customer cust = new Customer(
                    DBAccessory.getNextCustID(),
                    custNameTxt.getText(),
                    addy.getAddressID(),
                    1,
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
            );

            ApptSys.custList.add(cust);
            DBAccessory.addCustomer(cust);

        } else {
            selected.updateCust(
                    custNameTxt.getText(),
                    custAdd1Txt.getText(),
                    custAdd2Txt.getText(),
                    custPhoneTxt.getText(),
                    custCityTxt.getText(),
                    custCountryOpt.getValue(),
                    custPCTxt.getText()
            );
        }

        flagIsNew = false;
    }
    @FXML
    void onCnxBtn(ActionEvent event) {
        fillAllFields(selected);
    }

    /* Helper Methods */
    private void fillAllFields(Customer cust) {
        custNameTxt.setText(cust.getCustomerName());
        custPhoneTxt.setText(cust.getAddress().getPhone());
        custAdd1Txt.setText(cust.getAddress().getAddress1());
        custAdd2Txt.setText(cust.getAddress().getAddress2());
        custCityTxt.setText(cust.getAddress().getCity().getCity());
        custPCTxt.setText(cust.getAddress().getPostalCode());
        custCountryOpt.setValue(cust.getAddress().getCity().getCountry().getCountry());
    }
    private void clearAllFields() {
        custNameTxt.clear();
        custPhoneTxt.clear();
        custAdd1Txt.clear();
        custAdd2Txt.clear();
        custCityTxt.clear();
        custPCTxt.clear();
        custCountryOpt.getSelectionModel().selectFirst();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Set up TableView
        custListTbl.setItems(ApptSys.custList);
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custSelectModel = custListTbl.selectionModelProperty().get();
        custSelectModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillAllFields(newValue);
                selected = newValue;}
        }); //This lamda allows me to set an event listener and consumer in a single statement instead of needing to define a secondary helper method
        custSelectModel.select(1);

        try {
            custCountryOpt.setItems(DBAccessory.getCounNameList());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database unavailable. Please check your internet" +
                    " connection and restart the application.");
            alert.showAndWait();
            ApptSys.saveShut();
        }
    }
}