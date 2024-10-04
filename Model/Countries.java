/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * April 11, 2021
 * Scheduling System Application
 */

package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Countries {
    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    /**
     * constructor
     * @param Country_ID Country ID
     * @param Country Country
     * @param Create_Date Create Date
     * @param Created_By Created By
     * @param Last_Update Last Update
     * @param Las_Updated_By Last Updated By
     */
    public Countries(int Country_ID, String Country, LocalDateTime Create_Date, String Created_By, Timestamp Last_Update, String Las_Updated_By) {
        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Las_Updated_By;
    } // ENd constructor

    /**
     * @return id
     */
    public int getCountry_ID() { return Country_ID; }

    /**
     * @return country name
     */
    public String getCountry() { return Country; }

    /**
     * @return date created
     */
    public LocalDateTime getCreate_Date() { return Create_Date;} // END getCreate_Date

    /**
     * @return user who created it
     */
    public String getCreated_By() { return Created_By; }

    /**
     * @return timestamp last updated
     */
    public Timestamp getLast_Update() { return Last_Update; }

    /**
     * @return user who last updated it
     */
    public String getLast_Updated_By() { return Last_Updated_By; }

    /**
     * @param Country_ID id to set
     */
    public void setCountry_ID(int Country_ID) { this. Country_ID = Country_ID; }

    /**
     * @param Country name of country
     */
    public void setCountry(String Country) { this.Country = Country; }

    /**
     * @param Create_Date date created
     */
    public void  setCreate_Date(LocalDateTime Create_Date) { this.Create_Date = Create_Date; }

    /**
     * @param Created_By user who created it
     */
    public void setCreated_By(String Created_By) { this.Created_By = Created_By; }

    /**
     * @param Last_Update timestamp last updated
     */
    public void setLast_Update(Timestamp Last_Update) { this.Last_Update = Last_Update; }

    /**
     * @param Last_Updated_By user who last updated it
     */
    public void  setLast_Updated_By(String Last_Updated_By) { this.Last_Updated_By = Last_Updated_By; }

    @Override
    public String toString () {
        return (getCountry_ID() + " " + getCountry());
    } // END toString


} // END Countries class
