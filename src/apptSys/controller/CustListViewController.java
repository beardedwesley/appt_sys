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
    private TextField custNameTxt, custPhoneTxt, custAdd1Txt, custAdd2Txt, custCityTxt, custPCTxt, custCountryTxt;
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
                        custSelectModel.selectFirst();
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
            || custCityTxt.getText().isEmpty() || custPCTxt.getText().isEmpty() || custCountryTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must have a value.");
            alert.showAndWait();
            return;
        }

        //character limit enforcement
        if (custNameTxt.getText().length() > 45) {
            custNameTxt.setText(custNameTxt.getText().substring(0, 45));
        }
        if (custAdd1Txt.getText().length() > 50) {
            custAdd1Txt.setText(custAdd1Txt.getText().substring(0, 50));
        }
        if (custAdd2Txt.getText().length() > 50) {
            custAdd2Txt.setText(custAdd2Txt.getText().substring(0, 50));
        }
        if (custPCTxt.getText().length() > 10) {
            custPCTxt.setText(custPCTxt.getText().substring(0, 10));
        }
        if (custPhoneTxt.getText().length() > 20) {
            custPhoneTxt.setText(custPhoneTxt.getText().substring(0, 20));
        }
        if (custCityTxt.getText().length() > 50) {
            custCityTxt.setText(custCityTxt.getText().substring(0, 50));
        }
        if (custCountryTxt.getText().length() > 50) {
            custCountryTxt.setText(custCountryTxt.getText().substring(0, 50));
        }


        if (flagIsNew) {
            Country countryE = null;
            boolean flagCountryE = false;
            for (Country country : ApptSys.countryList) {
                if (country.getCountry().equalsIgnoreCase(custCountryTxt.getText())) {
                    countryE = country;
                    flagCountryE = true;
                    break;
                }
            }
            if (!flagCountryE) {
                countryE = new Country(
                    DBAccessory.getNextCountryID(),
                    custCountryTxt.getText(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
                );
                DBAccessory.addCountry(countryE);
                ApptSys.countryList.add(countryE);
            }

            City cityE = null;
            boolean flagCityE = false;
            for (City city : ApptSys.cityList) {
                if (city.getCity().equalsIgnoreCase(custCityTxt.getText())) {
                    cityE = city;
                    flagCityE = true;
                    break;
                }

            }
            if (!flagCityE) {
                cityE = new City(
                        DBAccessory.getNextCityID(),
                        custCityTxt.getText(),
                        countryE.getCountryID(),
                        LocalDateTime.now(),
                        ApptSys.currUser.getUserName(),
                        LocalDateTime.now(),
                        ApptSys.currUser.getUserName()
                );
                DBAccessory.addCity(cityE);
                ApptSys.cityList.add(cityE);
            }

            Address addressE = null;
            boolean flagAddressE = false;
            for (Address address : ApptSys.addList) {
                if (
                    address.getAddress1().equalsIgnoreCase(custAdd1Txt.getText()) &&
                    address.getAddress2().equalsIgnoreCase(custAdd2Txt.getText()) &&
                    address.getCityID() == cityE.getCityID() &&
                    address.getPhone().equalsIgnoreCase(custPhoneTxt.getText()) &&
                    address.getPostalCode().equalsIgnoreCase(custPCTxt.getText()) &&
                    address.getCity().getCountryID() == countryE.getCountryID()
                ) {
                    addressE = address;
                    flagAddressE = true;
                    break;
                }
            }
            if (!flagAddressE) {
                addressE = new Address(
                    DBAccessory.getNextAddID(),
                    custAdd1Txt.getText(),
                    custAdd2Txt.getText(),
                    cityE.getCityID(),
                    custPCTxt.getText(),
                    custPhoneTxt.getText(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
                );

                DBAccessory.addAddress(addressE);
                ApptSys.addList.add(addressE);
            }

            Customer cust = new Customer(
                    DBAccessory.getNextCustID(),
                    custNameTxt.getText(),
                    addressE.getAddressID(),
                    1,
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName(),
                    LocalDateTime.now(),
                    ApptSys.currUser.getUserName()
            );

            ApptSys.custList.add(cust);
            ApptSys.custNameList.add(cust.getCustomerName());
            DBAccessory.addCustomer(cust);
            flagIsNew = false;

        } else {
            selected.updateCust(
                    custNameTxt.getText(),
                    custAdd1Txt.getText(),
                    custAdd2Txt.getText(),
                    custPhoneTxt.getText(),
                    custCityTxt.getText(),
                    custCountryTxt.getText(),
                    custPCTxt.getText()
            );
            DBAccessory.updateCust(selected);
            DBAccessory.updateAddress(selected.getAddress());
        }
    }
    @FXML
    void onCnxBtn(ActionEvent event) {
        fillAllFields(selected);
        flagIsNew = false;
    }

    /* Helper Methods */
    private void fillAllFields(Customer cust) {
        custNameTxt.setText(cust.getCustomerName());
        custPhoneTxt.setText(cust.getAddress().getPhone());
        custAdd1Txt.setText(cust.getAddress().getAddress1());
        custAdd2Txt.setText(cust.getAddress().getAddress2());
        custCityTxt.setText(cust.getAddress().getCity().getCity());
        custPCTxt.setText(cust.getAddress().getPostalCode());
        custCountryTxt.setText(cust.getAddress().getCity().getCountry().getCountry());
    }
    private void clearAllFields() {
        custNameTxt.clear();
        custPhoneTxt.clear();
        custAdd1Txt.clear();
        custAdd2Txt.clear();
        custCityTxt.clear();
        custPCTxt.clear();
        custCountryTxt.clear();
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
                selected = newValue;
                flagIsNew = false;
            }
        }); //This lamda allows me to set an event listener and consumer in a single statement instead of needing to define a secondary helper method
        custListTbl.selectionModelProperty().get().selectFirst();
    }
}