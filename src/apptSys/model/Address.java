package apptSys.model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Address {

    /* Instance Variables */
    private int addressID, cityID;
    private String address1, address2, postalCode, phone, createdBy, lastUpdateBy;
    private LocalDateTime createDate, lastUpdate;
    private City city;

    /* Constructor */
    public Address(int addressID, String address1, String address2, int cityID, String postalCode, String phone,
                   LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) throws SQLException {
        this.addressID = addressID;
        this.address1 = address1;
        this.address2 = address2;
        this.cityID = cityID;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.city = DBAccessory.getCity(cityID);
    }

    /* Accessors */
    public int getAddressID() {
        return addressID;
    }
    public String getAddress1() {
        return address1;
    }
    public String getAddress2() {
        return address2;
    }
    public int getCityID() {
        return cityID;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getPhone() {
        return phone;
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
    public City getCity() { return city;}

    @Override
    public String toString() {

        StringBuilder address = new StringBuilder(this.address1);

        if (!(this.address2.isEmpty())) {
            address.append("\n").append(this.address2);
        }
        address.append("\n").append(this.city.toString()).append(" ").append(this.postalCode);

        return address.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return this.addressID == ((Address) obj).getAddressID() ||
                (this.address1.equals(((Address) obj).getAddress1()) && this.address2.equals(((Address) obj).getAddress2())
                        && this.phone.equals(((Address) obj).getPhone()) && this.postalCode.equals(((Address) obj).getPostalCode())
                        && this.city.equals(((Address) obj).getCity()));
    }

}