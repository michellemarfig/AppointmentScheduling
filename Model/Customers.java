/**
 * @author Michelle Martinez-Figueroa
 * WGU Software II
 * May 2, 2021
 * Scheduling System Application
 */
package Model;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customers {
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Division_ID;

    /** Constructor
     * @param id customer id to set
     * @param name customer name
     * @param address customer address
     * @param postal postal code
     * @param phone phone number
     * @param created date created
     * @param createdby user created by
     * @param last timestamp last updated
     * @param lastBy user las updated by
     * @param division division id
     */
    public Customers(int id, String name, String address, String postal, String phone, LocalDateTime created, String createdby, Timestamp last,
                        String lastBy, int division) {
        Customer_ID = id;
        Customer_Name = name;
        Address = address;
        Postal_Code = postal;
        Phone = phone;
        Create_Date = created;
        Created_By = createdby;
        Last_Update = last;
        Last_Updated_By = lastBy;
        Division_ID = division;
    }

    /**
     * @param id customer id to set
     */
    public void setCustomer_ID(int id) { Customer_ID = id; }

    /**
     * @param customer customer name to set
     */
    public void setCustomer_Name(String customer) { Customer_Name = customer;}

    /**
     * @param address address to set
     */
    public void setAddress(String address) { Address = address;}

    /**
     * @param postal postal code to set
     */
    public void  setPostal_Code(String postal) {Postal_Code = postal;}

    /**
     * @param phone phone to set
     */
    public void setPhone(String phone) { Phone = phone;}

    /**
     * @param created date created
     */
    public void setCreate_Date(LocalDateTime created) { Create_Date = created;}

    /**
     * @param createdBy user created by
     */
    public void setCreated_By(String createdBy) { Created_By = createdBy;}

    /**
     * @param last last timestamp to set
     */
    public void setLast_Update(Timestamp last) { Last_Update = last;}

    /**
     * @param lastBy user last updated by
     */
    public void setLast_Updated_By(String lastBy) { Last_Updated_By = lastBy;}

    /**
     * @param division division id to set
     */
    public void setDivision_ID(int division) { Division_ID = division;}

    /**
     * @return customer id
     */
    public int getCustomer_ID () {return Customer_ID;}

    /**
     * @return customer name
     */
    public String getCustomer_Name() {return Customer_Name;}

    /**
     * @return address
     */
    public String getAddress() {return Address;}

    /**
     * @return postal code
     */
    public String getPostal_Code() {return Postal_Code;}

    /**
     * @return phone
     */
    public String getPhone() {return Phone; }

    /**
     * @return create date
     */
    public LocalDateTime getCreate_Date() {return Create_Date;}

    /**
     * @return create date
     */
    public String getCreated_By() { return Created_By;}

    /**
     * @return timestamp last updated
     */
    public Timestamp getLast_Update() {return Last_Update;}

    /**
     * @return user last updated by
     */
    public String getLast_Updated_By() {return Last_Updated_By;}

    /**
     * @return division id
     */
    public int getDivision_ID() {return Division_ID;}

    /**
     * method to print customer
     * @return customer id and name
     */
    @Override
    public String toString() {
        return getCustomer_ID() + " " + getCustomer_Name();
    } // END toString method

} // END Customers class
