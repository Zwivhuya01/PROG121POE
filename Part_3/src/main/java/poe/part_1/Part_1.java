package poe.part_1;

import java.util.Scanner;

/**
 * 
 * @author Zwivhuya
 */
public class Part_1 {
    
    private static final int MESSAGE_LIMIT_WARNING = 5;
    
    private Part_1() {
        // Private constructor to prevent instantiation
    }
    
    public static void main(String[] args) {
        Login loginSystem = new Login();
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Registration and Login System ===\n");
            
            // Registration Phase
            if (!performRegistration(loginSystem, scanner)) {
                System.out.println("Registration failed. Cannot proceed.");
                return;
            }
            
            // Login Phase
            if (!performLogin(loginSystem, scanner)) {
                System.out.println("Login failed. Cannot access messaging features.");
                return;
            }
            
            // Proceed to messaging application
            runQuickChat(scanner);
        }
    }
    
    //Handles user registration process.
    
    
     
    private static boolean performRegistration(Login loginSystem, Scanner scanner) {
        System.out.println("--- Registration ---");
        
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Enter username (must contain underscore and be less 5 characters): ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special character): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter cell phone number (starting with +27): ");
        String phoneNumber = scanner.nextLine().trim();
        
        String registrationResult = loginSystem.registerUser(firstName, lastName, username, password, phoneNumber);
        
        System.out.println("\nRegistration Result:");
        System.out.println(registrationResult);
        System.out.println();
        
        return loginSystem.getRegisteredUser() != null;
    }
    
    
      //Handles user login process.
    
     
    private static boolean performLogin(Login loginSystem, Scanner scanner) {
        System.out.println("--- Login ---");
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();
        
        String loginStatus = loginSystem.returnLoginStatus(loginUsername, loginPassword);
        System.out.println("\nLogin Status:");
        System.out.println(loginStatus);
        
        return loginSystem.isLoggedIn();
    }
    
    
     //Runs the QuickChat messaging application.
    
    private static void runQuickChat(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("Welcome to QuickChat");
        System.out.println("========================================");
        
        int numMessages = getNumberOfMessages(scanner);
        Message[] messages = new Message[numMessages];
        int messageIndex = 0;
        boolean isRunning = true;
        
        while (isRunning && messageIndex < numMessages) {
            displayQuickChatMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    Message newMessage = createMessage(scanner, messageIndex + 1);
                    if (newMessage != null) {
                        messages[messageIndex] = newMessage;
                        messageIndex++;
                        displayMessageConfirmation(newMessage);
                    } else {
                        System.out.println("Failed to create message. Please try again.");
                    }
                    break;
                    
                case "2":
                    displayRecentMessages(messages, messageIndex);
                    break;
                    
                case "3":
                    displayExitSummary(messageIndex);
                    isRunning = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please select 1, 2, or 3.");
                    break;
            }
            
            // Show remaining message count
            if (messageIndex < numMessages && numMessages - messageIndex <= MESSAGE_LIMIT_WARNING) {
                System.out.printf("You have %d message(s) remaining.%n", numMessages - messageIndex);
            }
        }
        
        // Handle quota completion
        if (messageIndex >= numMessages && isRunning) {
            displayQuotaCompletion(numMessages);
        }
    }
    
    
    // Displays recently sent/stored messages.

    private static void displayRecentMessages(Message[] messages, int count) {
        System.out.println("\n========================================");
        System.out.println("       RECENTLY SENT/STORED MESSAGES");
        System.out.println("========================================");
        
        if (count == 0) {
            System.out.println("No messages have been sent or stored yet.");
            System.out.println("========================================");
            return;
        }
        
        System.out.printf("%-3s %-10s %-20s %-30s%n", "#", "Message ID", "Recipient", "Message Preview");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < count; i++) {
            Message msg = messages[i];
            String preview = msg.getMessageContent();
            if (preview.length() > 27) {
                preview = preview.substring(0, 24) + "...";
            }
            System.out.printf("%-3d %-10s %-20s %-30s%n", 
                i + 1, msg.getMessageID(), msg.getRecipientCell(), preview);
        }
        
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Total messages this session: " + count);
        System.out.println("========================================");
        
        // Option to view full message details
        if (count > 0) {
            System.out.print("\nEnter message number to view full details (or 0 to return): ");
            Scanner scanner = new Scanner(System.in);
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= count) {
                    displayFullMessageDetails(messages[choice - 1]);
                }
            } catch (NumberFormatException e) {
                // Ignore invalid input
            }
        }
    }
    
    
    // Displays full details of a selected message.
   
    private static void displayFullMessageDetails(Message message) {
        System.out.println("\n--- FULL MESSAGE DETAILS ---");
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipientCell());
        System.out.println("Full Message: " + message.getMessageContent());
        System.out.println("Message Length: " + message.getMessageContent().length() + " characters");
        System.out.println("----------------------------");
    }
    
    
    // Gets the number of messages the user wants to send.
    static int getNumberOfMessages(Scanner scanner) {
        System.out.print("\nHow many messages do you wish to send? ");
        
        while (true) {
            try {
                int numMessages = Integer.parseInt(scanner.nextLine().trim());
                if (numMessages > 0) {
                    return numMessages;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    
     // Displays the QuickChat main menu.
     
    private static void displayQuickChatMenu() {
        System.out.println("\n--- QuickChat Menu ---");
        System.out.println("1. Send Message");
        System.out.println("2. Show recently sent messages");
        System.out.println("3. Quit");
        System.out.print("Select an option: ");
    }
    
    
     //creates a new message by gathering user input.
     
  static Message createMessage(Scanner scanner, int messageNumber) {
        System.out.println("\n--- Create Message #" + messageNumber + " ---");
        
        // Auto-generate Message ID
        String messageID = generateMessageID();
        System.out.println("Auto-generated Message ID: " + messageID);
        
        System.out.print("Enter recipient cell phone number (starting with +27): ");
        String recipientCell = scanner.nextLine().trim();
        
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();
        
        // Validate message length
        String validationResult = Message.validateMessageLength(messageContent);
        System.out.println("Message validation: " + validationResult);
        
        if (!validationResult.equals("Message ready to send.")) {
            if (!confirmContinue(scanner)) {
                return null;
            }
        }
        
        // Create and hash the message
        Message tempMessage = new Message(messageID, "", recipientCell, messageContent);
        String messageHash = tempMessage.createMessageHash();
        System.out.println("Auto-generated Message Hash: " + messageHash);
        
        Message message = new Message(messageID, messageHash, recipientCell, messageContent);
        
        // Process message based on user choice
        int action = getMessageAction(scanner);
        String result = message.processMessage(action);
        System.out.println(result);
        
        return (action == 1 || action == 2) ? message : null;
    }
    
    
    private static String generateMessageID() {
        return String.format("MSG%03d", (int)(Math.random() * 1000));
    }
    
    
    // Asks user if they want to continue with a long message.
    
    private static boolean confirmContinue(Scanner scanner) {
        System.out.print("Do you want to continue with this message anyway? (yes/no): ");
        String continueChoice = scanner.nextLine().trim().toLowerCase();
        return continueChoice.equals("yes") || continueChoice.equals("y");
    }
    
    
      //Gets the user's action choice for the message.
     
    private static int getMessageAction(Scanner scanner) {
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Enter your choice: ");
        
        try {
            int action = Integer.parseInt(scanner.nextLine().trim());
            return (action >= 1 && action <= 3) ? action : 3;
        } catch (NumberFormatException e) {
            return 3;
        }
    }
    
    
     //Displays confirmation for a sent/stored message.
    
    private static void displayMessageConfirmation(Message message) {
        System.out.println("\n--- Message Confirmation ---");
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipientCell());
        System.out.println("Message: " + message.getMessageContent());
    }
    
   
    private static void displayExitSummary(int messageCount) {
        System.out.println("\n========================================");
        System.out.println("Total number of messages processed: " + Message.getTotalMessagesSent());
        System.out.println("Messages sent/stored this session: " + messageCount);
        System.out.println("Thank you for using QuickChat. Goodbye!");
        System.out.println("========================================");
    }
    
  
    private static void displayQuotaCompletion(int numMessages) {
        System.out.println("\n========================================");
        System.out.println("You have reached your message limit of " + numMessages + " messages.");
        System.out.println("Total number of messages sent: " + Message.getTotalMessagesSent());
        System.out.println("Thank you for using QuickChat. Goodbye!");
        System.out.println("========================================");
        
        // Display all messages and JSON storage
        System.out.println(Message.printAllMessages());
        System.out.println("\nMessages stored in JSON format:");
        System.out.println(Message.toJSON());
    }
}