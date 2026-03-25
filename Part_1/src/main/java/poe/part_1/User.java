/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part_1;


/**
 *
 * @Zwivhuya
 */
public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;
    
    // Constructor to create a new User object.
  
    public User(String firstName, String lastName, String username, 
                String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
  
    public String getFirstName() {
        return firstName;
    }
    
 
    public String getLastName() {
        return lastName;
    }
    
  
    public String getUsername() {
        return username;
    }
    

    public String getPassword() {
        return password;
    }
 
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
}