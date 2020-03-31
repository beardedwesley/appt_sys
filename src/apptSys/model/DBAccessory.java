package apptSys.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBAccessory {

    static Connection db;

    static {
        try {
            db = DriverManager.getConnection("jdbc:mysql://3.227.166.251/U05KRQ","U05KRQ","53688527658");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* "Instance" Variables */
    private static int nextApptID = -1;
    private static int nextCustID = -1;
    private static int nextAddID = -1;
    private static int nextCityID = -1;
    private static int nextCountryID = -1;

    /* Standard Accessors */
    public static int getNextApptID() throws SQLException {
        if (nextApptID == -1) {
            nextApptID = getLatestApptID() + 1;
        }
        int ret = nextApptID;
        nextApptID += 1;
        return ret;
    }
    public static int getNextCustID() throws SQLException {
        if (nextCustID == -1) {
            nextCustID = getLatestCustID() + 1;
        }
        int ret = nextCustID;
        nextCustID += 1;
        return ret;
    }
    public static int getNextAddID() throws SQLException {
        if (nextAddID == -1) {
            nextAddID = getLatestAddID() + 1;
        }
        int ret = nextAddID;
        nextAddID += 1;
        return ret;
    }
    public static int getNextCityID() throws SQLException {
        if (nextCityID == -1) {
            nextCityID = getLatestCityID() + 1;
        }
        int ret = nextCityID;
        nextCityID += 1;
        return ret;
    }
    public static int getNextCountryID() throws SQLException {
        if (nextCountryID == -1) {
            nextCountryID = getLatestCountryID() + 1;
        }
        int ret = nextCountryID;
        nextCountryID += 1;
        return ret;
    }

    /* Logging in */
    public static Integer verifyUser(String userName, String password) throws SQLException {

        if (userName.isEmpty() || userName.length() > 50) {

            return -1;
        }

        String sqlVerUser = "SELECT userID, password FROM user WHERE userName = ?";
        PreparedStatement userCheck = db.prepareStatement(sqlVerUser);
        userCheck.setString(1, userName);
        ResultSet rs;

        if (userCheck.execute()) {
            rs = userCheck.getResultSet();
            rs.next();
            int userID = rs.getInt("userId");
            String passDB = rs.getString("password");
            if (password.equals(passDB)){
                return userID;
            }
        } else {
            return null;
        }
        return -1;
    }

    /* Pulls data */
    //For home view
    public static ObservableList<Appointment> getAppointmentList (int userID) throws SQLException{
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        String sqlApptList = "SELECT * FROM appointment WHERE userId = ? ORDER BY start";
        PreparedStatement getApptList = db.prepareStatement(sqlApptList);
        getApptList.setString(1, ((Integer) userID).toString());
        ResultSet rs;
        if (getApptList.execute()) {
            rs = getApptList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Appointment currApt = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("start").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("end").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            appList.add(currApt);
        }

        return appList;
    }
    //For Option Boxes
    public static ObservableList<String> getUserNameList() throws SQLException {
        ObservableList<String> userList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlUserList = "SELECT DISTINCT userName FROM user ORDER BY userName";
        PreparedStatement getUserList = db.prepareStatement(sqlUserList);

        if (getUserList.execute()) {
            rs = getUserList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            userList.add(rs.getString("userName"));
        }

        return userList;
    }
    public static ObservableList<String> getTypeNameList() throws SQLException {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlTypeList = "SELECT DISTINCT type FROM appointment ORDER BY type";
        PreparedStatement getTypeList = db.prepareStatement(sqlTypeList);

        if(getTypeList.execute()) {
            rs = getTypeList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            typeList.add(rs.getString("type"));
        }

        return typeList;
    }
    public static ObservableList<String> getCustNameList() throws SQLException {
        ObservableList<String> custList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlCustList = "SELECT DISTINCT customerName FROM customer ORDER BY customerName";
        PreparedStatement getCustList = db.prepareStatement(sqlCustList);

        if (getCustList.execute()) {
            rs = getCustList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            custList.add(rs.getString("customerName"));
        }

        return custList;
    }
    //For Table Views
    public static ObservableList<Customer> getCustList() throws SQLException {
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlCustList = "SELECT * FROM customer";
        PreparedStatement getCustList = db.prepareStatement(sqlCustList);

        if(getCustList.execute()) {
            rs = getCustList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Customer currCust = new Customer(
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getInt("addressId"),
                    rs.getInt("active"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            custList.add(currCust);
        }

        return custList;
    }
    public static ObservableList<Address> getAddressList() throws SQLException {
        ObservableList<Address> addyList = FXCollections.observableArrayList();
        ResultSet rs;
        String sqlAddyList = "SELECT * FROM address";
        PreparedStatement getAddyList = db.prepareStatement(sqlAddyList);

        if(getAddyList.execute()) {
            rs = getAddyList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Address currAddy = new Address(
                    rs.getInt("addressId"),
                    rs.getString("address"),
                    rs.getString("address2"),
                    rs.getInt("cityId"),
                    rs.getString("postalCode"),
                    rs.getString("phone"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            addyList.add(currAddy);
        }

        return addyList;
    }
    public static ObservableList<City> getCityList() throws SQLException {
        ObservableList<City> cityList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlCityList = "SELECT * FROM city";
        PreparedStatement getCityList = db.prepareStatement(sqlCityList);

        if(getCityList.execute()) {
            rs = getCityList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            City currCity = new City(
                    rs.getInt("cityId"),
                    rs.getString("city"),
                    rs.getInt("countryID"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            cityList.add(currCity);
        }

        return cityList;
    }
    public static ObservableList<Country> getCountryList() throws SQLException {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlCountryList = "SELECT * FROM country";
        PreparedStatement getCountryList = db.prepareStatement(sqlCountryList);

        if(getCountryList.execute()) {
            rs = getCountryList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Country currCountry = new Country(
                    rs.getInt("countryId"),
                    rs.getString("country"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            countryList.add(currCountry);
        }

        return countryList;
    }
    //For back end representation
    public static Country getCountry(int countryID) throws SQLException{

        Country country;
        ResultSet rs;
        String sqlGetCountry = "SELECT * FROM country WHERE countryId = ?";
        PreparedStatement getCountry = db.prepareStatement(sqlGetCountry);
        getCountry.setString(1, ((Integer) countryID).toString());

        if (getCountry.execute()) {
            rs = getCountry.getResultSet();
        } else {return null;}

        rs.next();
        country = new Country(
                rs.getInt("countryId"),
                rs.getString("country"),
                TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                rs.getString("createdBy"),
                rs.getTimestamp("lastUpdate").toLocalDateTime(),
                rs.getString("lastUpdateBy")
        );

        return country;
    }
    public static City getCity(int cityID) throws SQLException {

        City city;
        ResultSet rs;
        String sqlGetCity = "SELECT * FROM city WHERE cityId = ?";
        PreparedStatement getCity = db.prepareStatement(sqlGetCity);
        getCity.setString(1, ((Integer) cityID).toString());

        if (getCity.execute()) {
            rs = getCity.getResultSet();
        } else {return null;}

        rs.next();
        city = new City(
                rs.getInt("cityId"),
                rs.getString("city"),
                rs.getInt("countryId"),
                TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                rs.getString("createdBy"),
                rs.getTimestamp("lastUpdate").toLocalDateTime(),
                rs.getString("lastUpdateBy")
        );

        return city;
    }
    public static Address getAddress(int addressID) throws SQLException {
        Address address;
        ResultSet rs;
        String sqlGetAddress = "SELECT * FROM address WHERE addressId = ?";
        PreparedStatement getAddress = db.prepareStatement(sqlGetAddress);
        getAddress.setString(1, ((Integer) addressID).toString());

        if (getAddress.execute()) {
            rs = getAddress.getResultSet();
        } else {return null;}

        rs.next();
        address = new Address(
                addressID,
                rs.getString("address"),
                rs.getString("address2"),
                rs.getInt("cityId"),
                rs.getString("postalCode"),
                rs.getString("phone"),
                TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                rs.getString("createdBy"),
                rs.getTimestamp("lastUpdate").toLocalDateTime(),
                rs.getString("lastUpdateBy")
        );

        return address;
    }
    public static Customer getCustomer(int customerID) throws SQLException {
        Customer customer;
        ResultSet rs;
        String sqlGetCust = "SELECT * FROM customer WHERE customerId = ?";
        PreparedStatement getCust = db.prepareStatement(sqlGetCust);
        getCust.setString(1, ((Integer) customerID).toString());

        if (getCust.execute()) {
            rs = getCust.getResultSet();
        } else {return null;}

        rs.next();
        customer = new Customer(
                rs.getInt("customerID"),
                rs.getString("customerName"),
                rs.getInt("addressID"),
                rs.getInt("active"),
                TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                rs.getString("createdBy"),
                rs.getTimestamp("lastUpdate").toLocalDateTime(),
                rs.getString("lastUpdateBy")
        );

        return customer;
    }
    //For reports views
    public static ObservableList<Appointment> getApptListByType(String type) throws SQLException{
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlApptTypeList = "SELECT * FROM appointment WHERE type = ?";
        PreparedStatement getApptTypeList = db.prepareStatement(sqlApptTypeList);
        getApptTypeList.setString(1, type);

        if(getApptTypeList.execute()) {
            rs = getApptTypeList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Appointment currApt = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("start").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("end").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            apptList.add(currApt);
        }

        return apptList;
    }
    private static int findUserID(String userName) throws SQLException{
        ResultSet rs;
        PreparedStatement findUser = db.prepareStatement("SELECT userId FROM user WHERE userName = ?");
        findUser.setString(1, userName);
        if (findUser.execute()) {
            rs = findUser.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("userId");
    }
    public static ObservableList<Appointment> getApptListByUser(String user) throws SQLException {
        Integer userID = findUserID(user);
        if (userID == -1) {return null;}

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlApptUserList = "SELECT * FROM appointment WHERE userId = ?";
        PreparedStatement getApptUserList = db.prepareStatement(sqlApptUserList);
        getApptUserList.setString(1, userID.toString());

        if(getApptUserList.execute()) {
            rs = getApptUserList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Appointment currApt = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("start").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("end").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            apptList.add(currApt);
        }

        return apptList;

    }
    private static int findCustID(String custName) throws SQLException{
        ResultSet rs;
        PreparedStatement findCust = db.prepareStatement("SELECT customerId FROM customer WHERE customerName = ?");
        findCust.setString(1, custName);
        if (findCust.execute()) {
            rs = findCust.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("customerId");
    }
    public static ObservableList<Appointment> getApptListByCust(String cust) throws SQLException {
        Integer custID = findCustID(cust);
        if (custID == -1) {return null;}

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        ResultSet rs;

        String sqlApptCustList = "SELECT * FROM appointment WHERE customerId = ?";
        PreparedStatement getApptCustList = db.prepareStatement(sqlApptCustList);
        getApptCustList.setString(1, custID.toString());

        if(getApptCustList.execute()) {
            rs = getApptCustList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Appointment currApt = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    TimeKeeper.convertToLocal(rs.getTimestamp("start").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("end").toLocalDateTime()),
                    TimeKeeper.convertToLocal(rs.getTimestamp("createDate").toLocalDateTime()),
                    rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate").toLocalDateTime(),
                    rs.getString("lastUpdateBy")
            );
            apptList.add(currApt);
        }

        return apptList;

    }

    /* Be ready to create new records since database does not manage/auto-increment keys */
    private static int getLatestApptID() throws SQLException {
        ResultSet rs;
        String sqlGetApptID = "SELECT MAX(appointmentId) AS appointmentID FROM appointment";
        PreparedStatement getApptID = db.prepareStatement(sqlGetApptID);

        if (getApptID.execute()) {
            rs = getApptID.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("appointmentID");
    }
    private static int getLatestCustID() throws SQLException {

        ResultSet rs;
        String sqlGetCustID = "SELECT MAX(customerId) AS customerID FROM customer";
        PreparedStatement getCustID = db.prepareStatement(sqlGetCustID);

        if (getCustID.execute()) {
            rs = getCustID.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("customerID");
    }
    private static int getLatestAddID() throws SQLException {
        ResultSet rs;
        String sqlGetAddID = "SELECT MAX(addressId) AS addressID FROM address";
        PreparedStatement getAddID = db.prepareStatement(sqlGetAddID);

        if (getAddID.execute()) {
            rs = getAddID.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("addressID");
    }
    private static int getLatestCityID() throws SQLException {
        ResultSet rs;
        String sqlGetCityID = "SELECT MAX(cityId) AS cityID FROM city";
        PreparedStatement getCityID = db.prepareStatement(sqlGetCityID);

        if (getCityID.execute()) {
            rs = getCityID.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("cityID");
    }
    private static int getLatestCountryID() throws SQLException {
        ResultSet rs;
        String sqlGetCountryID = "SELECT MAX(countryId) as countryID FROM country";
        PreparedStatement getCountryID = db.prepareStatement(sqlGetCountryID);

        if (getCountryID.execute()) {
            rs = getCountryID.getResultSet();
        } else {return -1;}

        rs.next();
        return rs.getInt("countryID");
    }

    /* Update current records */
    public static boolean updateAppt(Appointment updated) throws SQLException {

        String sqlApptUpdate = "UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, " +
                "contact = ?, type = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?";
        PreparedStatement apptUpd = db.prepareStatement(sqlApptUpdate);
        apptUpd.setString(1, ((Integer) updated.getCustomerID()).toString());
        apptUpd.setString(2, updated.getTitle());
        apptUpd.setString(3, updated.getDescription());
        apptUpd.setString(4, updated.getLocation());
        apptUpd.setString(5, updated.getContact());
        apptUpd.setString(6, updated.getType());
        apptUpd.setString(7, TimeKeeper.convertToDataZ(updated.getStart()).toString());
        apptUpd.setString(8, TimeKeeper.convertToDataZ(updated.getEnd()).toString());
        apptUpd.setString(9, updated.getLastUpdate().toString());
        apptUpd.setString(10, updated.getLastUpdateBy());
        apptUpd.setString(11, ((Integer) updated.getAppointmentID()).toString());

        return apptUpd.execute();
    }
    public static boolean updateCust(Customer updated) throws SQLException {

        String sqlCustUpdate = "UPDATE customer SET customerName = ?, addressId = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = ?";
        PreparedStatement custUpd = db.prepareStatement(sqlCustUpdate);
        custUpd.setString(1, updated.getCustomerName());
        custUpd.setString(2, ((Integer) updated.getAddressID()).toString());
        custUpd.setString(3, updated.getLastUpdate().toString());
        custUpd.setString(4, updated.getLastUpdateBy());
        custUpd.setString(5, ((Integer) updated.getCustomerID()).toString());

        return custUpd.execute();
    }
    public static boolean updateAddress(Address address) throws SQLException {

        String sqlAddUpdate = "UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressId = ?";
        PreparedStatement addUpd = db.prepareStatement(sqlAddUpdate);
        addUpd.setString(1, address.getAddress1());
        addUpd.setString(2, address.getAddress2());
        addUpd.setString(3, ((Integer) address.getCityID()).toString());
        addUpd.setString(4, address.getPostalCode());
        addUpd.setString(5, address.getPhone());
        addUpd.setString(6, address.getLastUpdate().toString());
        addUpd.setString(7, address.getLastUpdateBy());
        addUpd.setString(8, ((Integer) address.getAddressID()).toString());

        return addUpd.execute();
    }


    /* Remove current records */
    public static boolean deleteAppointment(Appointment delAppt) throws SQLException {
        String sqlDelAppt = "DELETE FROM appointment WHERE appointmentId = ?";
        PreparedStatement removeAppt = db.prepareStatement(sqlDelAppt);
        removeAppt.setString(1, ((Integer) delAppt.getAppointmentID()).toString());
        return removeAppt.execute();
    }
    public static void deleteCustomer(Customer delCust) throws SQLException {
        String sqlDelCustAppt = "DELETE FROM appointment WHERE customerId = ?";
        PreparedStatement removeCust = db.prepareStatement(sqlDelCustAppt);
        removeCust.setString(1, ((Integer) delCust.getCustomerID()).toString());
        removeCust.execute();

        String sqlDelCust = "DELETE FROM customer WHERE customerId = ?";
        removeCust = db.prepareStatement(sqlDelCust);
        removeCust.setString(1, ((Integer) delCust.getCustomerID()).toString());
        removeCust.execute();
    }

    /* Insert new records */
    public static boolean addAppt(Appointment newAppt) throws SQLException {
        String sqlAddAppt = "INSERT INTO appointment VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addApt = db.prepareStatement(sqlAddAppt);

        addApt.setString(1, ((Integer) newAppt.getAppointmentID()).toString());
        addApt.setString(2, ((Integer) newAppt.getCustomerID()).toString());
        addApt.setString(3, ((Integer) newAppt.getUserID()).toString());
        addApt.setString(4, newAppt.getTitle());
        addApt.setString(5, newAppt.getDescription());
        addApt.setString(6, newAppt.getLocation());
        addApt.setString(7, newAppt.getContact());
        addApt.setString(8, newAppt.getType());
        addApt.setString(9, newAppt.getUrl());
        addApt.setString(10, TimeKeeper.convertToDataZ(newAppt.getStart()).toString());
        addApt.setString(11, TimeKeeper.convertToDataZ(newAppt.getEnd()).toString());
        addApt.setString(12, TimeKeeper.convertToDataZ(newAppt.getCreateDate()).toString());
        addApt.setString(13, newAppt.getCreatedBy());
        addApt.setString(14, newAppt.getLastUpdate().toString());
        addApt.setString(15, newAppt.getLastUpdateBy());

        return addApt.execute();
    }
    public static boolean addCustomer(Customer newCust) throws SQLException {
        String sqlAddCust = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addCust = db.prepareStatement(sqlAddCust);

        addCust.setString(1, ((Integer) newCust.getCustomerID()).toString());
        addCust.setString(2, newCust.getCustomerName());
        addCust.setString(3, ((Integer) newCust.getAddressID()).toString());
        addCust.setString(4, ((Integer) newCust.getActive()).toString());
        addCust.setString(5, TimeKeeper.convertToDataZ(newCust.getCreateDate()).toString());
        addCust.setString(6, newCust.getCreatedBy());
        addCust.setString(7, newCust.getLastUpdate().toString());
        addCust.setString(8, newCust.getLastUpdateBy());

        return addCust.execute();
    }
    public static boolean addAddress(Address newAddy) throws SQLException {
        String sqlAddAddy = "INSERT INTO address VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addAddy = db.prepareStatement(sqlAddAddy);

        addAddy.setString(1, ((Integer) newAddy.getAddressID()).toString());
        addAddy.setString(2, newAddy.getAddress1());
        addAddy.setString(3, newAddy.getAddress2());
        addAddy.setString(4, ((Integer) newAddy.getCityID()).toString());
        addAddy.setString(5, newAddy.getPostalCode());
        addAddy.setString(6, newAddy.getPhone());
        addAddy.setString(7, TimeKeeper.convertToDataZ(newAddy.getCreateDate()).toString());
        addAddy.setString(8, newAddy.getCreatedBy());
        addAddy.setString(9, newAddy.getLastUpdate().toString());
        addAddy.setString(10, newAddy.getLastUpdateBy());

        return addAddy.execute();
    }
    public static boolean addCity(City newCity) throws SQLException {
        String sqlAddCity = "INSERT INTO city VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addCity = db.prepareStatement(sqlAddCity);

        addCity.setString(1, ((Integer) newCity.getCityID()).toString());
        addCity.setString(2, newCity.getCity());
        addCity.setString(3, ((Integer) newCity.getCountryID()).toString());
        addCity.setString(4, TimeKeeper.convertToDataZ(newCity.getCreateDate()).toString());
        addCity.setString(5, newCity.getCreatedBy());
        addCity.setString(6, newCity.getLastUpdate().toString());
        addCity.setString(7, newCity.getLastUpdateBy());

        return addCity.execute();
    }
    public static boolean addCountry (Country newCountry) throws SQLException {
        String sqlAddCountry = "INSERT INTO country VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement addCountry = db.prepareStatement(sqlAddCountry);

        addCountry.setString(1, ((Integer) newCountry.getCountryID()).toString());
        addCountry.setString(2, newCountry.getCountry());
        addCountry.setString(3, TimeKeeper.convertToDataZ(newCountry.getCreateDate()).toString());
        addCountry.setString(4, newCountry.getCreatedBy());
        addCountry.setString(5, newCountry.getLastUpdate().toString());
        addCountry.setString(6, newCountry.getLastUpdateBy());

        return addCountry.execute();
    }

}






















