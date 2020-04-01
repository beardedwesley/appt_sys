package apptSys.model;

import apptSys.ApptSys;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customer {

    /* Instance Variables */
    private int customerID, addressID, active;
    private String customerName, createdBy, lastUpdateBy;
    private LocalDateTime createDate, lastUpdate;
    private Address address;

    /* Constructors */
    public Customer(int customerID, String customerName, int addressID, LocalDateTime createDate, String createdBy) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.addressID = addressID;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = createDate;
        this.lastUpdateBy = createdBy;
        this.address = DBAccessory.getAddress(addressID);
    }
    public Customer(int customerID, String customerName, int addressID, int active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.addressID = addressID;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.address = DBAccessory.getAddress(addressID);
    }

    /* Standard Accessors */
    public int getCustomerID() {
        return customerID;
    }
    public String getCustomerName() {
        return customerName;
    }
    public int getAddressID() {
        return addressID;
    }
    public int getActive() {
        return active;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    public Address getAddress() {return address;}

    /* Custom Accessors (for displaying in TableView) */
    public String getPhoneNumber() { return address.getPhone(); }
    public String getCity() { return address.getCity().getCity(); }
    public String getCountry() { return address.getCity().getCountry().getCountry(); }

    /* Custom Mutator, pushes update to Database */
    public void updateCust(String nwCustName, String nwCustAdd1, String nwCustAdd2, String nwCustPhone,
                           String nwCustCity, String nwCustCountry, String nwCustPC) throws SQLException {

        if(!(this.customerName.equals(nwCustName))) {
            this.customerName = nwCustName;
        }

        //Look to see if updated address matches an existing address in database (if it has changed)
        int countryMatch = -1, cityMatch = -1, addressMatch = -1;

        for (int i = 0; i < ApptSys.countryList.size(); i++)
            for (Country currCountry : ApptSys.countryList) {
                if (currCountry.getCountry().equals(nwCustCountry)) {
                    countryMatch = currCountry.getCountryID();
                }
        }
        if (countryMatch == -1) {
            Country nwCountry = new Country(DBAccessory.getNextCountryID(), nwCustCountry, LocalDateTime.now(),
                    ApptSys.currUser.getUserName(), LocalDateTime.now(), ApptSys.currUser.getUserName());
            DBAccessory.addCountry(nwCountry);
            ApptSys.countryList.add(nwCountry);
            countryMatch = nwCountry.getCountryID();
        }
        for (City currCity : ApptSys.cityList) {
            if (currCity.getCity().equals(nwCustCity) && currCity.getCountryID() == countryMatch) {
                cityMatch = currCity.getCityID();
            }
        }
        if (cityMatch == -1) {
            City nwCity = new City(DBAccessory.getNextCityID(), nwCustCity, countryMatch, LocalDateTime.now(),
                    ApptSys.currUser.getUserName(), LocalDateTime.now(), ApptSys.currUser.getUserName());
            DBAccessory.addCity(nwCity);
            ApptSys.cityList.add(nwCity);
            cityMatch = nwCity.getCityID();
        }

        for (Address currAddy : ApptSys.addList) {
            if (currAddy.getAddress1().equals(nwCustAdd1) && currAddy.getAddress2().equals(nwCustAdd2) &&
                    currAddy.getPostalCode().equals(nwCustPC) && currAddy.getCityID() == cityMatch &&
                    currAddy.getPhone().equalsIgnoreCase(nwCustPhone)) {
                addressMatch = currAddy.getAddressID();
            }
        }
        if(addressMatch == -1) {
            Address nwAddress = new Address(DBAccessory.getNextAddID(), nwCustAdd1, nwCustAdd2, cityMatch, nwCustPC,
                    nwCustPhone, LocalDateTime.now(), ApptSys.currUser.getUserName(),
                    LocalDateTime.now(), ApptSys.currUser.getUserName());
            DBAccessory.addAddress(nwAddress);
            ApptSys.addList.add(nwAddress);
            addressMatch = nwAddress.getAddressID();
        }
        this.lastUpdate = LocalDateTime.now();
        this.lastUpdateBy = ApptSys.currUser.getUserName();
        this.addressID = addressMatch;
        this.address = DBAccessory.getAddress(addressMatch);
    }

    @Override
    public String toString() {
        return customerName;
    }
}