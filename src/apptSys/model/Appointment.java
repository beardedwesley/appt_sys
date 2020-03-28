package apptSys.model;

import apptSys.ApptSys;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment implements Comparable{

    /* Instance Variables */
    private int appointmentID, userID, customerID;
    private String title, description, location, contact, type, url, createdBy, lastUpdateBy;
    private LocalDateTime start, end, createDate, lastUpdate;
    private Customer customer;

    /* Constructors */
    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location,
                       String contact, String type, String url, String createdBy) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = LocalDateTime.now();
        this.end = LocalDateTime.now();
        this.createDate = LocalDateTime.now();
        this.createdBy = createdBy;
        this.lastUpdate = LocalDateTime.now();
        this.lastUpdateBy = createdBy;
    }
    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location,
                       String contact, String type, String url, LocalDateTime start, LocalDateTime end, LocalDateTime createDate,
                       String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdatedBy;
        this.customer = DBAccessory.getCustomer(customerID);
    }

    /* Standard Accessors */
    public int getAppointmentID() {
        return appointmentID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public int getUserID() {
        return userID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getContact() {
        return contact;
    }
    public String getType() {
        return type;
    }
    public String getUrl() {
        return url;
    }
    public LocalDateTime getStart() {
        return start;
    }
    public LocalDateTime getEnd() {
        return end;
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
    public Customer getCustomer() { return customer;}

    /* Custom Accessors */
    public LocalDate getDate() { return start.toLocalDate();}
    public LocalTime getStartTime() {return start.toLocalTime();}
    public LocalTime getEndTime() { return end.toLocalTime();}
    public String getDetails() {
        return new StringBuilder().append(title).append("\n").append(start.toLocalTime()).toString();
    }

    /* Custom Mutator */
    public void updateAppt(int nwCustID, String nwTitle, String nwDescription, String nwLocation, String nwContact,
                           String nwType, LocalDateTime nwStart, LocalDateTime nwEnd) {
        if(this.customerID != nwCustID) {
            this.customerID = nwCustID;
        }
        if(!(this.title.equals(nwTitle))) {
            this.title = nwTitle;
        }
        if(!(this.description.equals(nwDescription))) {
            this.description = nwDescription;
        }
        if(!(this.location.equals(nwLocation))) {
            this.location = nwLocation;
        }
        if(!(this.contact.equals(nwContact))) {
            this.contact = nwContact;
        }
        if(!(this.type.equals(nwType))) {
            this.type = nwType;
        }
        if(!(this.start.isEqual(nwStart))) {
            this.start = nwStart;
        }
        if(!(this.end.isEqual(nwEnd))) {
            this.end = nwEnd;
        }
        this.lastUpdate = LocalDateTime.now();
        this.lastUpdateBy = ApptSys.currUser.getUserName();

        try {
            if (DBAccessory.updateAppt(this)) {
                return;
            } else {
                //TODO advise of error as appointment could not be updated
            }
        }
        catch (SQLException e) {
            //TODO Warn user that database not reachable or submission does not meet criteria, please try again
        }
    }

    @Override
    public int compareTo(Object o) throws NullPointerException, ClassCastException{
        if (this.start.isBefore(((Appointment) o).getStart())) {
            return 1;
        } else if (this.start.isEqual(((Appointment) o).getStart())) {
            return 0;
        } else {
            return -1;
        }

    }
}
