/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package poe.part_1;

import java.util.Scanner;

/**
 *
 * @Zwivhuya
 */
public class Part_1 {

    public static void main(String[] args) {
      Login loginSystem = new Login();
        Scanner scanner = new Scanner(System.in);
        
       
        System.out.println("=== Registration and Login System ===\n");
        
        // Registration Phase
        System.out.println("--- Registration ---");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter username (must contain underscore and be <=5 chars): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter cell phone number (with +27 code): ");
        String phoneNumber = scanner.nextLine();
        
        String registrationResult = loginSystem.registerUser(firstName, lastName, 
                                                              username, password, 
                                                              phoneNumber);
        System.out.println("\nRegistration Result:");
        System.out.println(registrationResult);
        
        // Login Phase
        if (loginSystem.getRegisteredUser() != null) {
            System.out.println("\n--- Login ---");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            String loginStatus = loginSystem.returnLoginStatus(loginUsername, loginPassword);
            System.out.println("\nLogin Status:");
            System.out.println(loginStatus);
        }
        
        scanner.close();
    
    
    }
}
