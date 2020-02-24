package apptSys.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {

    private int appointmentID, userID, customerID;
    private String title, description, location, contact, type, url, createdBy, updatedBy;
    private LocalDateTime start, end, created, updated;

    public Appointment(int appointmentID, int userID, int customerID, String title, String description, String location, String contact, String type, String url, String createdBy) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.customerID = customerID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
        this.start = LocalDateTime.now();
        this.end = LocalDateTime.now();
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location, String contact, String type, String url, Timestamp start, Timestamp end, Timestamp created, String createdBy, Timestamp updated, String updatedBy) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.customerID = customerID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.start = start.toLocalDateTime();
        this.end = end.toLocalDateTime();
        this.created = created.toLocalDateTime();
        this.updated = updated.toLocalDateTime();
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCustomerID() {
        return customerID;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void updateAppt(int nwCustID, String nwTitle, String nwDescription, String nwLocation, String nwContact, String nwType, LocalDateTime nwStart, LocalDateTime nwEnd) {
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

}
