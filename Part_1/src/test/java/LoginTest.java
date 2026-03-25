/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import poe.part_1.Login;


/**
 *
 * @Zwivhuya
 */
public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
    
    @AfterEach
    public void tearDown() {
        login = null;
    }
    
    // Tests for checkUserName method
    @Test
    public void testCheckUserName_ValidUsername() {
        assertTrue(login.checkUserName("zette_"));
        assertTrue(login.checkUserName("a_b"));
        assertTrue(login.checkUserName("abc_1"));
    }
    
    @Test
    public void testCheckUserName_NoUnderscore() {
        assertFalse(login.checkUserName("zettend"));
        assertFalse(login.checkUserName("zette ndou"));
        assertFalse(login.checkUserName("zette"));
    }
    
    @Test
    public void testCheckUserName_TooLong() {
        assertFalse(login.checkUserName("zette_ndou_123")); 
        assertFalse(login.checkUserName("abc_de_fg")); 
    }
    
    @Test
    public void testCheckUserName_Null() {
        assertFalse(login.checkUserName(null));
    }
    
    @Test
    public void testCheckUserName_EmptyString() {
        assertFalse(login.checkUserName(""));
    }
    
    // Tests for checkPasswordComplexity method
    @Test
    public void testCheckPasswordComplexity_ValidPassword() {
        assertTrue(login.checkPasswordComplexity("Password123!"));
        assertTrue(login.checkPasswordComplexity("StrongP@ssw0rd"));
        assertTrue(login.checkPasswordComplexity("Test1234@"));
        assertTrue(login.checkPasswordComplexity("A1!bcdefg"));
    }
    
    @Test
    public void testCheckPasswordComplexity_TooShort() {
        assertFalse(login.checkPasswordComplexity("Pass1!"));
        assertFalse(login.checkPasswordComplexity("A1!"));
        assertFalse(login.checkPasswordComplexity("Abc12!"));
    }
    
    @Test
    public void testCheckPasswordComplexity_NoCapitalLetter() {
        assertFalse(login.checkPasswordComplexity("password123!"));
        assertFalse(login.checkPasswordComplexity("test1234@"));
        assertFalse(login.checkPasswordComplexity("weakpass1!"));
    }
    
    @Test
    public void testCheckPasswordComplexity_NoNumber() {
        assertFalse(login.checkPasswordComplexity("Password!@#$"));
        assertFalse(login.checkPasswordComplexity("StrongPass!"));
        assertFalse(login.checkPasswordComplexity("NoNumbersHere!"));
    }
    
    @Test
    public void testCheckPasswordComplexity_NoSpecialCharacter() {
        assertFalse(login.checkPasswordComplexity("Password123"));
        assertFalse(login.checkPasswordComplexity("StrongPass123"));
        assertFalse(login.checkPasswordComplexity("Test1234"));
    }
    
    @Test
    public void testCheckPasswordComplexity_Null() {
        assertFalse(login.checkPasswordComplexity(null));
    }
    
    @Test
    public void testCheckPasswordComplexity_EmptyString() {
        assertFalse(login.checkPasswordComplexity(""));
    }
    
    // Tests for checkCellPhoneNumber method
    @Test
    public void testCheckCellPhoneNumber_ValidPhoneNumbers() {
        assertTrue(login.checkCellPhoneNumber("+27123456789"));
        assertTrue(login.checkCellPhoneNumber("+27821234567"));
        assertTrue(login.checkCellPhoneNumber("+2712345678"));
        assertTrue(login.checkCellPhoneNumber("+2712345"));
    }
    
    @Test
    public void testCheckCellPhoneNumber_NoInternationalCode() {
        assertFalse(login.checkCellPhoneNumber("0123456789"));
        assertFalse(login.checkCellPhoneNumber("1234567890"));
        assertFalse(login.checkCellPhoneNumber("+1234567890")); 
        assertFalse(login.checkCellPhoneNumber("27123456789")); 
    }
    
    @Test
    public void testCheckCellPhoneNumber_TooLongAfterCode() {
        assertFalse(login.checkCellPhoneNumber("+271234567890"));
        assertFalse(login.checkCellPhoneNumber("+27123456789123")); 
    }
    
    @Test
    public void testCheckCellPhoneNumber_ContainsLetters() {
        assertFalse(login.checkCellPhoneNumber("+27123ABC567"));
        assertFalse(login.checkCellPhoneNumber("+27ABC123456"));
    }
    
    @Test
    public void testCheckCellPhoneNumber_Null() {
        assertFalse(login.checkCellPhoneNumber(null));
    }
    
    @Test
    public void testCheckCellPhoneNumber_EmptyString() {
        assertFalse(login.checkCellPhoneNumber(""));
    }
    
    // Tests for registerUser method
    @Test
    public void testRegisterUser_AllValid() {
        String result = login.registerUser("", "zette", "ndou", 
                                          "Password123!", "+27123456789");
        
        assertNotNull(login.getRegisteredUser());
        assertEquals("zette", login.getRegisteredUser().getFirstName());
        assertEquals("ndou", login.getRegisteredUser().getLastName());
        assertEquals("zette_", login.getRegisteredUser().getUsername());
        assertEquals("Password123!", login.getRegisteredUser().getPassword());
        assertEquals("+27123456789", login.getRegisteredUser().getCellPhoneNumber());
        
        assertTrue(result.contains("Username successfully captured"));
        assertTrue(result.contains("Password successfully captured"));
        assertTrue(result.contains("Cell phone number successfully added"));
    }
    
    @Test
    public void testRegisterUser_InvalidUsername() {
        String result = login.registerUser("zette", "ndou", "zettendou", 
                                          "Password123!", "+27123456789");
        
        assertNull(login.getRegisteredUser());
        assertTrue(result.contains("Username is not correctly formatted"));
        assertFalse(result.contains("Password successfully captured"));
        assertFalse(result.contains("Cell phone number successfully added"));
    }
    
    @Test
    public void testRegisterUser_InvalidPassword() {
        String result = login.registerUser("zette", "ndou", "zette_", 
                                          "password", "+27123456789");
        
        assertNull(login.getRegisteredUser());
        assertFalse(result.contains("Username successfully captured"));
        assertTrue(result.contains("Password is not correctly formatted"));
        assertFalse(result.contains("Cell phone number successfully added"));
    }
    
    @Test
    public void testRegisterUser_InvalidPhone() {
        String result = login.registerUser("zette", "ndou", "zette_", 
                                          "Password123!", "0123456789");
        
        assertNull(login.getRegisteredUser());
        assertFalse(result.contains("Username successfully captured"));
        assertFalse(result.contains("Password successfully captured"));
        assertTrue(result.contains("Cell phone number incorrectly formatted"));
    }
    
    @Test
    public void testRegisterUser_AllInvalid() {
        String result = login.registerUser("zette", "ndou", "zettendou", 
                                          "pass", "0123456789");
        
        assertNull(login.getRegisteredUser());
        assertTrue(result.contains("Username is not correctly formatted"));
        assertTrue(result.contains("Password is not correctly formatted"));
        assertTrue(result.contains("Cell phone number incorrectly formatted"));
    }
    
    // Tests for loginUser method
    @Test
    public void testLoginUser_ValidCredentials() {
        login.registerUser("zette", "ndou", "zette_ndou", "Password123!", "+27123456789");
        
        assertTrue(login.loginUser("zette_ndou", "Password123!"));
    }
    
    @Test
    public void testLoginUser_InvalidUsername() {
        login.registerUser("zette", "ndou", "zette_ndou", "Password123!", "+27123456789");
        
        assertFalse(login.loginUser("wrong_user", "Password123!"));
    }
    
    @Test
    public void testLoginUser_InvalidPassword() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        
        assertFalse(login.loginUser("zette_", "WrongPassword!"));
    }
    
    @Test
    public void testLoginUser_NoUserRegistered() {
        assertFalse(login.loginUser("any_user", "any_pass"));
    }
    
    @Test
    public void testLoginUser_NullCredentials() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        
        assertFalse(login.loginUser(null, "Password123!"));
        assertFalse(login.loginUser("zette_", null));
        assertFalse(login.loginUser(null, null));
    }
    
    // Tests for returnLoginStatus method
    @Test
    public void testReturnLoginStatus_ValidLogin() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        String status = login.returnLoginStatus("zette_", "Password123!");
        
        assertEquals("Welcome John Doe it is great to see you again.", status);
    }
    
    @Test
    public void testReturnLoginStatus_InvalidLogin() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        String status = login.returnLoginStatus("wrong_user", "Password123!");
        
        assertEquals("Username or password incorrect, please try again.", status);
    }
    
    @Test
    public void testReturnLoginStatus_NoUserRegistered() {
        String status = login.returnLoginStatus("any_user", "any_pass");
        
        assertEquals("Username or password incorrect, please try again.", status);
    }
    
    // Tests for getters
    @Test
    public void testGetRegisteredUser_AfterRegistration() {
        assertNull(login.getRegisteredUser());
        
        login.registerUser("zette", "ndou", "zette", "Password123!", "+27123456789");
        
        assertNotNull(login.getRegisteredUser());
        assertEquals("zette", login.getRegisteredUser().getFirstName());
    }
    
    @Test
    public void testGetRegistrationMessage_AfterRegistration() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        
        assertNotNull(login.getRegistrationMessage());
        assertTrue(login.getRegistrationMessage().contains("Username successfully captured"));
    }
    
    @Test
    public void testGetLoginStatus_AfterLoginAttempt() {
        login.registerUser("zette", "ndou", "zette_", "Password123!", "+27123456789");
        
        login.returnLoginStatus("zette_", "Password123!");
        assertNotNull(login.getLoginStatus());
        assertEquals("Welcome zette ndou it is great to see you again.", login.getLoginStatus());
        
        login.returnLoginStatus("wrong", "wrong");
        assertEquals("Username or password incorrect, please try again.", login.getLoginStatus());
    }
}