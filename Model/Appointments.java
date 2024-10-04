/**
 * @author Michelle Martinez-Figueroa
 * July 27, 2021
 * Scheduling System Application
 */

package Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointments {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    public Appointments(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                            LocalDateTime created, String createdBy, Timestamp last, String lastBy, int custID, int userID, int contID) {
        Appointment_ID = id;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date = created;
        Created_By = createdBy;
        Last_Update = last;
        Last_Updated_By = lastBy;
        Customer_ID = custID;
        User_ID = userID;
        Contact_ID = contID;
    } // ENd constructor

    /**
     * mehtod to set appointment id
     * @param id id to set
     */
    public void setAppointment_ID(int id) {
        Appointment_ID = id;
    }

    /**
     * method to set title
     * @param title title to set
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * method to set description
     * @param description description to set
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * method to set location
     * @param location location to set
     */
     public void setLocation(String location) {
         Location = location;
    }

    /**
     * method to set type
     * @param type type to set
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * method to set start time
     * @param start start time to set
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * method to set end time
     * @param end end time to set
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * method to set create date
     * @param create_Date date created
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * method to set created by
     * @param created_By created by to set
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * method to set last update
     * @param last_Update timestamp
     */
    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    /**
     * method to set last updated by
     * @param last_Updated_By last updated by to set
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * method to set customer id
     * @param customer_ID customer id to set
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * method to set user id
     * @param user_ID user id to set
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    /**
     * methos to set contact id
     * @param contact_ID contact id to set
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * method to get appointment id
     * @return appointment id
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * method to get title
     * @return title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * method to get description
     * @return description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * method to get location
     * @return location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * method to get type
     * @return type
     */
    public String getType() {
        return Type;
    }

    /**
     * method to get start time
     * @return start time
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * method to get end time
     * @return end time
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * method to get create date
     * @return create date
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**+
     * method to get created by
     * @return created by
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * method to get last update
     * @return timestamp
     */
    public Timestamp getLast_Update() {
        return Last_Update;
    }

    /**
     * method to get last updated by
     * @return last updated by
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * method to get customer id
     * @return customer id
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * method to get user id
     * @return user id
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * method to get contact id
     * @return contact id
     */
    public int getContact_ID() {
        return Contact_ID;
    }
} // ENd Appointments class
