/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * May 2, 2021
 * Scheduling System Application
 */
package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Users {
    private int User_ID;
    private String User_Name;
    private String Password;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    /**
     * constructor
     * @param id user id
     * @param user user name
     * @param pass password
     * @param created date created
     * @param createdBy user created by
     * @param last timestamp last updated
     * @param lastBy user last updated by
     */
    public Users (int id, String user, String pass, LocalDateTime created, String createdBy, Timestamp last, String lastBy) {
        User_ID = id;
        User_Name = user;
        Password = pass;
        Create_Date = created;
        Created_By = createdBy;
        Last_Update = last;
        Last_Updated_By = lastBy;
    }

    /**
     * @param id id to set
     */
    public void setUser_ID(int id) {User_ID = id;}

    /**
     * @param user user name to set
     */
    public void setUser_Name(String user) { User_Name = user;}

    /**
     * @param pass password to set
     */
    public void setPassword(String pass) {Password = pass;}


    /**
     * @param created date created
     */
    public void setCreate_Date(LocalDateTime created) {Create_Date = created;}

    /**
     * @param createdBy user created by
     */
    public void setCreated_By(String createdBy) {Created_By = createdBy;}


    /**
     * @param last Timestamp last updated
     */
    public void setLast_Update(Timestamp last) {Last_Update = last;}

    /**
     * @param lastBy user last updated by
     */
    public void setLast_Updated_By(String lastBy) {Last_Updated_By = lastBy;}

    /**
     * @return user id
     */
    public int getUser_ID(){return User_ID;}

    /**+
     * @return user name
     */
    public String getUser_Name() {return User_Name;}

    /**
     * @return password
     */
    public String getPassword() {return Password;}

    /**
     * @return date created
     */
    public LocalDateTime getCreate_Date() {return Create_Date;}

    /**
     * @return user created by
     */
    public String getCreated_By() {return Created_By;}

    /**
     * @return timestamp last updated
     */
    public Timestamp getLast_Update() { return Last_Update;}

    /**
     * @return user last updated by
     */
    public String getLast_Updated_By() {return Last_Updated_By;}

    @Override
    public String toString() {
        return getUser_ID() + " " + getUser_Name();
    } // END toString


} // END Users class
