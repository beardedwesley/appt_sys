package apptSys.model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customer {

    private int customerID, addressID, active;
    private String customerName, createdBy, updatedBy;
    private LocalDateTime created, updated;

    public Customer(int customerID, int addressID, int active, String customerName, String createdBy, LocalDateTime created) {
        this.customerID = customerID;
        this.addressID = addressID;
        this.active = active;
        this.customerName = customerName;
        this.createdBy = createdBy;
        this.created = created;
        this.updatedBy = createdBy;
        this.updated = created;
    }

    public Customer(int customerID, int addressID, int active, String customerName, String createdBy, String updatedBy, LocalDateTime created, LocalDateTime updated) {
        this.customerID = customerID;
        this.addressID = addressID;
        this.active = active;
        this.customerName = customerName;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.created = created;
        this.updated = updated;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getAddressID() {
        return addressID;
    }

    public int getActive() {
        return active;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void updateCust(int nwAddressID, String nwCustName) {

        if(this.addressID != nwAddressID) {
            this.addressID = nwAddressID;
        }
        if(!(this.customerName.equals(nwCustName))) {
            this.customerName = nwCustName;
        }

        try {
            if (DBAccessory.updateCust(this)) {
                return;
            } else {
                //TODO advise of error as customer could not be updated
            }
        }
        catch (SQLException e) {
            //TODO Warn user that database not reachable or submission does not meet criteria, please try again
        }

    }
}
