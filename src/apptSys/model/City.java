package apptSys.model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class City {

    /* Instance Variables */
    private int cityID, countryID;
    private String city, createdBy, lastUpdateBy;
    private LocalDateTime createDate,lastUpdate;
    private Country country;

    /* Constructor */
    public City(int cityID, String city, int countryID, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                String lastUpdatedBy) throws SQLException {
        this.cityID = cityID;
        this.countryID = countryID;
        this.city = city;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdatedBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.country = DBAccessory.getCountry(countryID);
    }

    /* Accessors */
    public int getCityID() {
        return cityID;
    }
    public String getCity() {
        return city;
    }
    public int getCountryID() {
        return countryID;
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
    public Country getCountry() { return country; }

    @Override
    public String toString() {
        return city + ", " + this.country.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return this.cityID == ((City) obj).getCityID() ||
                (this.city.equals(((City) obj).getCity()) && this.country.equals(((City) obj).getCountry()));
    }
}