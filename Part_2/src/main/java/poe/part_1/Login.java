/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part_1;

/**
 *
 * @author Zwivhuya
 */
public class Login {
    
    private User registeredUser;
    private String registrationMessage;
    private String loginStatus;
    private boolean isLoggedIn;
    
    /**
     * Default constructor initializes empty user state.
     */
    public Login() {
        this.registeredUser = null;
        this.registrationMessage = "";
        this.loginStatus = "";
        this.isLoggedIn = false;
    }
    
    // This method ensures username contains underscore and length <= 5
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        // Check if username contains underscore and length <= 5
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }
        
        // Check length
        if (password.length() < 8) {
            return false;
        }
        
        // Check for capital letter, number, and special character
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    private boolean isOnlyDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
 
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        
        // Check if starts with +27 (South Africa international code)
        if (cellPhoneNumber.startsWith("+27")) {
            String numberAfterCode = cellPhoneNumber.substring(3);
            return numberAfterCode.length() <= 10 && isOnlyDigits(numberAfterCode);
        }
        
        return false;
    }
    
    // Registers a new user with the provided information.
    public String registerUser(String firstName, String lastName, String username, 
                              String password, String cellPhoneNumber) {
        // Validate username
        boolean isUsernameValid = checkUserName(username);
        boolean isPasswordValid = checkPasswordComplexity(password);
        boolean isPhoneNumValid = checkCellPhoneNumber(cellPhoneNumber);
        
        // Build registration message
        StringBuilder message = new StringBuilder();
        
        if (!isUsernameValid) {
            message.append("Username is not correctly formatted; please ensure that your username ")
                   .append("contains an underscore and is no more than five characters in length.\n");
        } else {
            message.append("Username successfully captured.\n");
        }
        
        if (!isPasswordValid) {
            message.append("Password is not correctly formatted; please ensure that the password ")
                   .append("contains at least eight characters, a capital letter, a number, and a special character.\n");
        } else {
            message.append("Password successfully captured.\n");
        }
        
        if (!isPhoneNumValid) {
            message.append("Cell phone number incorrectly formatted or does not contain international code.\n");
        } else {
            message.append("Cell phone number successfully added.\n");
        }
        
        // If all validations pass, register the user
        if (isUsernameValid && isPasswordValid && isPhoneNumValid) {
            this.registeredUser = new User(firstName, lastName, username, password, cellPhoneNumber);
            registrationMessage = message.toString().trim();
            return registrationMessage;
        } else {
            registrationMessage = message.toString().trim();
            return registrationMessage;
        }
    }
    
    // Verifies if the provided login credentials match the registered user.
    public boolean loginUser(String username, String password) {
        if (registeredUser == null) {
            return false;
        }
        
        boolean loginSuccess = registeredUser.getUsername().equals(username) && 
                              registeredUser.getPassword().equals(password);
        
        if (loginSuccess) {
            isLoggedIn = true;
        }
        
        return loginSuccess;
    }
 
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password) && registeredUser != null) {
            loginStatus = "Welcome " + registeredUser.getFirstName() + " " + 
                         registeredUser.getLastName() + " it is great to see you again.";
            return loginStatus;
        } else {
            loginStatus = "Username or password incorrect, please try again.";
            return loginStatus;
        }
    }
    
    // Check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public User getRegisteredUser() {
        return registeredUser;
    }
    
    public String getRegistrationMessage() {
        return registrationMessage;
    }

    public String getLoginStatus() {
        return loginStatus;
    }
    
    // Logout method
    public void logout() {
        isLoggedIn = false;
    }
}