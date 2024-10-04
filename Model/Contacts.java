/**
 * @author Michelle Martinez-Figueroa
 * May 3, 2021
 * Scheduling System Application
 */
package Model;

public class Contacts {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    public Contacts(int id, String name, String email) {
        Contact_ID = id;
        Contact_Name = name;
        Email = email;
    }

    /**
     * @param id contact id to set
     */
    public void setContact_ID(int id) {Contact_ID = id;}

    /**
     * @param name contact name to set
     */
    public void setContact_Name(String name) {Contact_Name = name;}

    /**
     * @param email email to set
     */
    public void setEmail(String email) { Email = email;}

    /**
     * @return contact id
     */
    public int getContact_ID() {return Contact_ID;}

    /**
     * @return contact name
     */
    public String getContact_Name() {return Contact_Name;}

    /**
     * @return email address
     */
    public String getEmail() {return Email;}

    /**
     * method to print contacts
     * @return customer id and name as string
     */
    @Override
    public String toString() {
        return getContact_ID() + " " + getContact_Name();
    } // END toString method




} // END Contacts class
