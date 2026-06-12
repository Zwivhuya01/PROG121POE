package poe.part_1;

/**
 * 
 * @author Zwivhuya
 */
public class Login {
    
    private static final int USERNAME_MAX_LENGTH = 5;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String SOUTH_AFRICA_CODE = "+27";
    
    private User registeredUser;
    private String registrationMessage;
    private String loginStatus;
    private boolean isLoggedIn;
    
    

     
    public Login() {
        this.registeredUser = null;
        this.registrationMessage = "";
        this.loginStatus = "";
        this.isLoggedIn = false;
    }
    
     // This method ensures username contains underscore and length <= 5
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= USERNAME_MAX_LENGTH;
    }
    
    
    // Validates password complexity requirements:
 
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
            return false;
        }
        
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
    
// Check for capital letter, number, and special character
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
    
    // Check if starts with +27 (South Africa international code)
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null) {
            return false;
        }
        
        if (cellPhoneNumber.startsWith(SOUTH_AFRICA_CODE)) {
            String numberAfterCode = cellPhoneNumber.substring(SOUTH_AFRICA_CODE.length());
            return numberAfterCode.length() <= 10 && isOnlyDigits(numberAfterCode);
        }
        
        return false;
    }
    
    
    public String registerUser(String firstName, String lastName, String username, 
                              String password, String cellPhoneNumber) {
        boolean isUsernameValid = checkUserName(username);
        boolean isPasswordValid = checkPasswordComplexity(password);
        boolean isPhoneNumValid = checkCellPhoneNumber(cellPhoneNumber);
        
        StringBuilder message = new StringBuilder();
        
        // Username validation
        message.append(isUsernameValid ? "Username successfully captured.\n" 
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n");
        
        // Password validation
        message.append(isPasswordValid ? "Password successfully captured.\n" 
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        
        // Phone validation
        message.append(isPhoneNumValid ? "Cell phone number successfully added.\n" 
                : "Cell phone number incorrectly formatted or does not contain international code.\n");
        
        // Register user only if all validations pass
        if (isUsernameValid && isPasswordValid && isPhoneNumValid) {
            this.registeredUser = new User(firstName, lastName, username, password, cellPhoneNumber);
        }
        
        registrationMessage = message.toString().trim();
        return registrationMessage;
    }
    
  
    public boolean loginUser(String username, String password) {
        if (registeredUser == null) {
            return false;
        }
        
        boolean loginSuccess = registeredUser.getUsername().equals(username) && 
                              registeredUser.getPassword().equals(password);
        
        if (loginSuccess) {
            isLoggedIn = true;
            loginStatus = "Welcome " + registeredUser.getFirstName() + " " + 
                         registeredUser.getLastName() + " it is great to see you again.";
        } else {
            loginStatus = "Username or password incorrect, please try again.";
        }
        
        return loginSuccess;
    }
    
   
    public String returnLoginStatus(String username, String password) {
        loginUser(username, password);
        return loginStatus;
    }
    
   
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
    

    public void logout() {
        isLoggedIn = false;
    }
}