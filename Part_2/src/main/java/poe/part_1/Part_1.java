/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package poe.part_1;

import java.util.Scanner;

/**
 *
 * @author Zwivhuya
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
            
            // Check if login was successful before proceeding to messaging
            if (loginSystem.loginUser(loginUsername, loginPassword)) {
                System.out.println("\n" + loginStatus);
                
                // Proceed to QuickChat application
                runQuickChat(scanner);
            } else {
                System.out.println("Login failed. Cannot access messaging features.");
            }
        } else {
            System.out.println("Registration failed. Cannot proceed to login.");
        }
        
        scanner.close();
    }
    
    /**
     * Runs the QuickChat messaging application
     * @param scanner Scanner object for user input
     */
    private static void runQuickChat(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("Welcome to QuickChat");
        System.out.println("========================================");
        
        // Ask user how many messages they wish to send
        System.out.print("\nHow many messages do you wish to send? ");
        int numMessages;
        while (true) {
            try {
                numMessages = Integer.parseInt(scanner.nextLine());
                if (numMessages > 0) {
                    break;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        
        // Array to store messages
        Message[] messages = new Message[numMessages];
        int messageIndex = 0;
        boolean isRunning = true;
        
        while (isRunning && messageIndex < numMessages) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1. Send Message");
            System.out.println("2. Show recently sent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    // Create and send a new message
                    Message newMessage = createMessage(scanner, messageIndex + 1);
                    if (newMessage != null) {
                        messages[messageIndex] = newMessage;
                        messageIndex++;
                        
                        // Display the sent message
                        System.out.println("\n--- Message Sent ---");
                        System.out.println("Message ID: " + newMessage.getMessageID());
                        System.out.println("Message Hash: " + newMessage.getMessageHash());
                        System.out.println("Recipient: " + newMessage.getRecipientCell());
                        System.out.println("Message: " + newMessage.getMessageContent());
                    } else {
                        System.out.println("Failed to create message. Please try again.");
                    }
                    break;
                    
                case "2":
                    System.out.println("\nComing Soon - This feature is still in development.");
                    break;
                    
                case "3":
                    // Display total messages sent before quitting
                    System.out.println("\n========================================");
                    System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    System.out.println("========================================");
                    isRunning = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please select 1, 2, or 3.");
                    break;
            }
        }
        
        // If all messages quota is reached
        if (messageIndex >= numMessages && isRunning) {
            System.out.println("\n========================================");
            System.out.println("You have reached your message limit of " + numMessages + " messages.");
            System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
            System.out.println("Thank you for using QuickChat. Goodbye!");
            System.out.println("========================================");
            
            // Optionally display all messages and JSON storage
            System.out.println(Message.printMessages());
            System.out.println("\nMessages stored in JSON format:");
            System.out.println(Message.storeMessagesToJSON());
        }
    }
    
    /**
     * Creates a new message by gathering user input
     * @param scanner Scanner object
     * @param messageNumber Current message number
     * @return Created Message object or null if invalid
     */
    private static Message createMessage(Scanner scanner, int messageNumber) {
        System.out.println("\n--- Create Message #" + messageNumber + " ---");
        
        // Auto-generate Message ID (3-digit number)
        String messageID = String.format("MSG%03d", (int)(Math.random() * 1000));
        System.out.println("Auto-generated Message ID: " + messageID);
        
        // Get recipient number
        System.out.print("Enter recipient cell phone number (with +27 code): ");
        String recipientCell = scanner.nextLine();
        
        // Get message content
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();
        
        // Validate message length
        String validationResult = Message.validateMessageLength(messageContent);
        System.out.println("Message validation: " + validationResult);
        
        if (!validationResult.equals("Message ready to send.")) {
            System.out.print("Do you want to continue with this message anyway? (yes/no): ");
            String continueChoice = scanner.nextLine().toLowerCase();
            if (!continueChoice.equals("yes")) {
                return null;
            }
        }
        
        // Create message hash
        Message tempMessage = new Message(messageID, "", recipientCell, messageContent);
        String messageHash = tempMessage.createMessageHash();
        System.out.println("Auto-generated Message Hash: " + messageHash);
        
        // Create the message
        Message message = new Message(messageID, messageHash, recipientCell, messageContent);
        
        // Ask user what to do with the message
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Enter your choice: ");
        
        int action;
        try {
            action = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            action = 3;
        }
        
        String result = message.sentMessage(action);
        System.out.println(result);
        
        // Return the message only if it was sent or stored
        if (action == 1 || action == 2) {
            return message;
        } else {
            return null;
        }
    }
}