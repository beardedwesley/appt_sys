package apptSys.model;

import java.time.LocalDateTime;

public class Country {

    /* Instance Variables */
    private int countryID;
    private String country, createdBy, lastUpdateBy;
    private LocalDateTime createDate, lastUpdate;

    /* Constructor */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.countryID = countryID;
        this.country = country;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    /* Accessors */
    public int getCountryID() {
        return countryID;
    }
    public String getCountry() {
        return country;
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

    @Override
    public String toString() {
        return country;
    }
    @Override
    public boolean equals(Object obj) {
        return this.countryID == ((Country) obj).getCountryID() || this.country.equals(((Country) obj).getCountry());
    }
}