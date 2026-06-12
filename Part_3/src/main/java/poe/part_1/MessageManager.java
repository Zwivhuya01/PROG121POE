package poe.part_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * 
 * @author Zwivhuya
 */
public class MessageManager {
    
    private static final String STORAGE_FILE = "stored_messages.json";
    private static final int MAX_DISPLAY_LENGTH = 27;
    private static final int TRUNCATE_LENGTH = 24;
    
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();
    
    private MessageManager() {
        // Private constructor to prevent instantiation
    }
    
    
    //Populates the system with test data for demonstration purposes.
     
    public static void populateWithTestData() {
        clearAllData();
        
        // Create test messages
        Message sent1 = createTestMessage("MSG001", "+27834557896", "Did you get the cake?");
        sentMessages.add(sent1);
        addToTracking(sent1);
        
        Message stored1 = createTestMessage("MSG002", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        storedMessages.add(stored1);
        addToTracking(stored1);
        
        Message disregarded1 = createTestMessage("MSG003", "+27834484567", "Yohoooo, I am at your gate.");
        disregardedMessages.add(disregarded1);
        addToTracking(disregarded1);
        
        Message sent2 = createTestMessage("MSG004", "0838884567", "It is dinner time!");
        sentMessages.add(sent2);
        addToTracking(sent2);
        
        Message stored2 = createTestMessage("MSG005", "+27838884567", "Ok, I am leaving without you.");
        storedMessages.add(stored2);
        addToTracking(stored2);
        
        System.out.println("Test data populated successfully.");
    }
    
    
    // Creates a test message with hash.
     
    private static Message createTestMessage(String id, String recipient, String content) {
        Message msg = new Message(id, "", recipient, content);
        msg.setMessageHash(msg.createMessageHash());
        return msg;
    }
    
    
    // Adds message to tracking lists.
     
    private static void addToTracking(Message msg) {
        messageIDs.add(msg.getMessageID());
        messageHashes.add(msg.getMessageHash());
    }
    
    /**
     * Clears all data collections.
     */
    private static void clearAllData() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
    }
    
    
    // Loads stored messages from JSON file.
     
    public static void loadStoredMessagesFromJSON() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STORAGE_FILE))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            
            if (jsonContent.length() > 0) {
                JSONArray jsonArray = new JSONArray(jsonContent.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonMsg = jsonArray.getJSONObject(i);
                    Message msg = new Message(
                        jsonMsg.getString("messageID"),
                        jsonMsg.getString("messageHash"),
                        jsonMsg.getString("recipientCell"),
                        jsonMsg.getString("messageContent")
                    );
                    storedMessages.add(msg);
                    messageIDs.add(msg.getMessageID());
                    messageHashes.add(msg.getMessageHash());
                }
            }
        } catch (IOException e) {
            System.out.println("No existing storage file found. Starting fresh.");
        }
    }
    
    
    // Saves stored messages to JSON file.
     
    public static void saveStoredMessagesToJSON() {
        JSONArray jsonArray = new JSONArray();
        for (Message msg : storedMessages) {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("messageID", msg.getMessageID());
            jsonMsg.put("messageHash", msg.getMessageHash());
            jsonMsg.put("recipientCell", msg.getRecipientCell());
            jsonMsg.put("messageContent", msg.getMessageContent());
            jsonArray.put(jsonMsg);
        }
        
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            writer.write(jsonArray.toString(4));
        } catch (IOException e) {
            System.err.println("Error saving messages: " + e.getMessage());
        }
    }
    
    
    // Displays sender and recipient for all stored messages.
     
    public static void displaySenderAndRecipient() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }
        
        System.out.println("\n=== SENDER AND RECIPIENT ===");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            System.out.printf("%d. Sender: Developer | Recipient: %s%n", i + 1, msg.getRecipientCell());
        }
    }
    
    
    public static void displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }
        
        Message longest = storedMessages.get(0);
        for (Message msg : storedMessages) {
            if (msg.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = msg;
            }
        }
        
        System.out.println("\n=== LONGEST STORED MESSAGE ===");
        System.out.println("Message: \"" + longest.getMessageContent() + "\"");
        System.out.println("Length: " + longest.getMessageContent().length() + " characters");
        System.out.println("Recipient: " + longest.getRecipientCell());
    }
    
    
      //Searches for a message by its ID.
    
    public static void searchByMessageID(String messageID) {
        // Search in sent messages
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(messageID)) {
                displayMessageDetails(msg);
                return;
            }
        }
        
        // Search in stored messages
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                displayMessageDetails(msg);
                return;
            }
        }
        
        System.out.println("No message found with ID: " + messageID);
    }
    
    
    // Displays message details.
   
    private static void displayMessageDetails(Message msg) {
        System.out.println("\nRecipient: " + msg.getRecipientCell());
        System.out.println("Message: \"" + msg.getMessageContent() + "\"");
    }
    
    // Searches for all messages sent to a specific recipient.
     
    
    public static void searchMessagesByRecipient(String recipient) {
        List<Message> found = new ArrayList<>();
        for (Message msg : storedMessages) {
            if (msg.getRecipientCell().equals(recipient)) {
                found.add(msg);
            }
        }
        
        System.out.println("\n=== MESSAGES FOR RECIPIENT: " + recipient + " ===");
        if (found.isEmpty()) {
            System.out.println("No messages found for this recipient.");
        } else {
            for (int i = 0; i < found.size(); i++) {
                System.out.printf("%d. Message: \"%s\"%n", i + 1, found.get(i).getMessageContent());
            }
        }
    }
    
    
     // Deletes a message by its hash value.

    public static boolean deleteMessageByHash(String messageHash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(messageHash)) {
                String deletedContent = storedMessages.get(i).getMessageContent();
                storedMessages.remove(i);
                saveStoredMessagesToJSON();
                System.out.println("Message: \"" + deletedContent + "\" successfully deleted.");
                return true;
            }
        }
        System.out.println("No message found with hash: " + messageHash);
        return false;
    }
    
    
    //Displays a comprehensive report of all stored messages.
     
    public static void displayFullReport() {
        System.out.println("\n================================================");
        System.out.println("       FULL STORED MESSAGES REPORT");
        System.out.println("================================================");
        
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }
        
        System.out.printf("%-12s %-20s %-15s %-30s%n", "Message ID", "Message Hash", "Recipient", "Message");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Message msg : storedMessages) {
            String content = msg.getMessageContent();
            if (content.length() > MAX_DISPLAY_LENGTH) {
                content = content.substring(0, TRUNCATE_LENGTH) + "...";
            }
            System.out.printf("%-12s %-20s %-15s %-30s%n", 
                msg.getMessageID(), msg.getMessageHash(), msg.getRecipientCell(), content);
        }
        
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Total Stored Messages: " + storedMessages.size());
    }
    
    
    // Displays the stored messages menu and handles user input.
    
    public static void showStoredMessagesMenu(Scanner scanner) {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for all messages for a recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report");
            System.out.println("g. Return to Main Menu");
            System.out.print("Choice: ");
            
            String choice = scanner.nextLine().toLowerCase();
            
            switch (choice) {
                case "a":
                    displaySenderAndRecipient();
                    break;
                case "b":
                    displayLongestStoredMessage();
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    searchByMessageID(scanner.nextLine());
                    break;
                case "d":
                    System.out.print("Enter recipient cell number: ");
                    searchMessagesByRecipient(scanner.nextLine());
                    break;
                case "e":
                    System.out.print("Enter Message Hash: ");
                    deleteMessageByHash(scanner.nextLine());
                    break;
                case "f":
                    displayFullReport();
                    break;
                case "g":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
    // Getters - return defensive copies
    public static List<Message> getSentMessages() { return new ArrayList<>(sentMessages); }
    public static List<Message> getDisregardedMessages() { return new ArrayList<>(disregardedMessages); }
    public static List<Message> getStoredMessages() { return new ArrayList<>(storedMessages); }
    public static List<String> getMessageHashes() { return new ArrayList<>(messageHashes); }
    public static List<String> getMessageIDs() { return new ArrayList<>(messageIDs); }
}