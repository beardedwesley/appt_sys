package apptSys.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class DBAccessory {

    static Connection db;

    private static String sqlUsrEvtLst = "SELECT appointmentId FROM appointment WHERE userId EQUALS ?";
    private static String sqlCustLst = "SELECT customerId, customerName FROM customer";
    private static String sqlEvtDet = "SELECT * FROM appointment WHERE appointmentId EQUALS ?";


    static {
        try {
            db = DriverManager.getConnection("jdbc:mysql://3.227.166.251/U05KRQ","U05KRQ","53688527658");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer verifyUser(String userName, String password) throws SQLException {

        String sqlVerUser = "SELECT userID, password FROM user WHERE userName EQUALS ?";

        if (userName.length() > 50) {

            return -1;
        }

        PreparedStatement userCheck = db.prepareStatement(sqlVerUser);
        userCheck.setString(1, userName);
        ResultSet rs;

        if (userCheck.execute()) {
            rs = userCheck.getResultSet();
            int userID = rs.getInt(1);
            String passDB = rs.getString(2);
            if (password.equals(passDB)){
                return userID;
            }
        } else {
            return null;
        }
        return -1;
    }

    public static boolean updateAppt(Appointment updated) throws SQLException {

        String sqlApptUpdate = "UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, " +
                "contact = ?, type = ?, start = ?, end = ?, lastUpdate = ?, lastUpdatedBy = ? WHERE appointmentId EQUALS ?";
        PreparedStatement apptUpd = db.prepareStatement(sqlApptUpdate);
        apptUpd.setString(1, ((Integer) updated.getCustomerID()).toString());
        apptUpd.setString(2, updated.getTitle());
        apptUpd.setString(3, updated.getDescription());
        apptUpd.setString(4, updated.getLocation());
        apptUpd.setString(5, updated.getContact());
        apptUpd.setString(6, updated.getType());
        apptUpd.setString(7, updated.getStart().toString());
        apptUpd.setString(8, updated.getEnd().toString());
        apptUpd.setString(9, updated.getUpdated().toString());
        apptUpd.setString(10, updated.getUpdatedBy());
        apptUpd.setString(11, ((Integer) updated.getAppointmentID()).toString());

        return apptUpd.execute();
    }

    public static boolean updateCust(Customer updated) throws SQLException {

        String sqlCustUpdate = "UPDATE customer SET customerName = ?, addressId = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId EQUALS ?";
        PreparedStatement custUpd = db.prepareStatement(sqlCustUpdate);
        custUpd.setString(1, updated.getCustomerName());
        custUpd.setString(2, ((Integer) updated.getAddressID()).toString());
        custUpd.setString(3, updated.getUpdated().toString());
        custUpd.setString(4, updated.getUpdatedBy());
        custUpd.setString(5, ((Integer) updated.getCustomerID()).toString());

        return custUpd.execute();
    }

    public static ObservableList<Appointment> getAppointmentList (int userID) throws SQLException{
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        String sqlApptList = "SELECT * FROM appointment WHERE userId EQUALS ?";
        PreparedStatement getApptList = db.prepareStatement(sqlApptList);
        getApptList.setString(1, ((Integer) userID).toString());
        ResultSet rs;
        if (getApptList.execute()) {
            rs = getApptList.getResultSet();
        } else {return null;}

        while (rs.next()) {
            Appointment currApt = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"),
                    rs.getInt("userId"), rs.getString("title"), rs.getString("description"),
                    rs.getString("location"), rs.getString("contact"), rs.getString("type"),
                    rs.getString("url"), rs.getTimestamp("start"), rs.getTimestamp("end"),
                    rs.getTimestamp("createdDate"), rs.getString("createdBy"),
                    rs.getTimestamp("updatedDate"), rs.getString("updated"));
            appList.add(currApt);
        }

        return appList;
    }

}
