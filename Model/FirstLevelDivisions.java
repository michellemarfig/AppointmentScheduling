/**
 * @author Michelle Martinez-Figueroa
 * May 2, 2021
 * Scheduling System Application
 */

package Model;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivisions{
    private int Division_ID;
    private String Division;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Country_ID;

    /**
     * constructor
     * @param id division id
     * @param division division name
     * @param created date created
     * @param createdBy user created by
     * @param last date last updated
     * @param lastBy user last updated by
     * @param country country id
     */
    public FirstLevelDivisions( int id, String division, LocalDateTime created, String createdBy, Timestamp last, String lastBy, int country) {
        Division_ID = id;
        Division = division;
        Create_Date = created;
        Created_By = createdBy;
        Last_Update = last;
        Last_Updated_By = lastBy;
        Country_ID = country;
    } // END constructor

    /**
     * @param id id to set
     */
    public void setDivision_ID(int id) { Division_ID = id; }

    /**
     * @param name division name to set
     */
    public void setDivision(String name) { Division = name;}

    /**
     * @param created date created
     */
    public void setCreate_Date(LocalDateTime created){ Create_Date = created; }

    /**
     * @param createdBy user created by
     */
    public void setCreated_By(String createdBy) { Created_By = createdBy; }

    /**
     * @param last timestamp last updated
     */
    public void setLast_Update( Timestamp last ) { Last_Update = last; }

    /**
     * @param lastBy user last updated by
     */
    public void setLast_Updated_By(String lastBy ) { Last_Updated_By = lastBy; }

    /**
     * @param country country id to set
     */
    public void setCountry_ID(int country) { Country_ID = country; }

    /**
     * @return division id
     */
    public int getDivision_ID() { return Division_ID; }

    /**
     * @return division name
     */
    public String getDivision() { return Division; }

    /**
     * @return date created
     */
    public LocalDateTime getCreate_Date() { return Create_Date;}

    /**
     * @return user created by
     */
    public String getCreated_By() { return Created_By;}

    /** @return timestamp last updated
     */
    public Timestamp getLast_Update() { return Last_Update;}

    /**
     * @return user last updated by
     */
    public String getLast_Updated_By() { return Last_Updated_By;}

    /**
     * @return country id
     */
    public int getCountry_ID() { return Country_ID;}

    @Override
    public String toString() {
        return (getDivision_ID() + " " + getDivision());
    } // END toString


} // END FirstLevelDivisions class
