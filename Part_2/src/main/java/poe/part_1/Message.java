/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part_1;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Zwivhuya
 */
public class Message {
    
    private String messageID;
    private String messageHash;
    private String recipientCell;
    private String messageContent;
    private static List<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    
    // Constructor
    public Message(String messageID, String messageHash, String recipientCell, String messageContent) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
    }
    
    /**
     * Ensures that the message ID is not more than ten characters.
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        if (messageID == null) {
            return false;
        }
        return messageID.length() <= 10;
    }
    
    /**
     * Ensures that the recipient cell number is no more than ten characters long and starts with a code.
     * @return success message or error message
     */
    public String checkRecipientCell() {
        if (recipientCell == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // Check if starts with +27 (South Africa international code)
        if (recipientCell.startsWith("+27")) {
            String numberAfterCode = recipientCell.substring(3);
            if (numberAfterCode.length() <= 10 && isOnlyDigits(numberAfterCode)) {
                return "Cell phone number successfully captured.";
            }
        }
        
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
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
    
    /**
     * Creates and returns the Message Hash.
     * Format: XX:Y:CONTENT (where XX is length, Y is number of vowels)
     * @return message hash
     */
    public String createMessageHash() {
        if (messageContent == null) {
            return "";
        }
        
        int length = messageContent.length();
        int vowelCount = 0;
        
        for (char c : messageContent.toLowerCase().toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowelCount++;
            }
        }
        
        // Get first 5 characters of content for the hash (or entire content if shorter)
        String contentPrefix = messageContent.length() > 5 ? 
                               messageContent.substring(0, 5) : messageContent;
        
        return String.format("%02d:%d:%s", length, vowelCount, contentPrefix);
    }
    
    /**
     * Allows the user to choose if they want to send, store, or disregard the message.
     * @param choice 1=Send, 2=Store, 3=Disregard
     * @return result message
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                sentMessages.add(this);
                return "Message successfully stored.";
            case 3:
                return "Press 0 to delete the message.";
            default:
                return "Invalid choice.";
        }
    }
    
    /**
     * Returns all the messages sent while the program is running.
     * @return formatted string of all messages
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("\n=== All Sent/Stored Messages ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            result.append(String.format("%d. Message ID: %s | Message Hash: %s | Recipient: %s | Message: %s\n",
                    i + 1, msg.messageID, msg.messageHash, msg.recipientCell, msg.messageContent));
        }
        return result.toString();
    }
    
    /**
     * Returns the total number of messages sent.
     * @return total messages sent
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    /**
     * Stores messages in JSON format.
     * @return JSON array of all messages
     */
    public static String storeMessagesToJSON() {
        JSONArray jsonArray = new JSONArray();
        
        for (Message msg : sentMessages) {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("messageID", msg.messageID);
            jsonMsg.put("messageHash", msg.messageHash);
            jsonMsg.put("recipientCell", msg.recipientCell);
            jsonMsg.put("messageContent", msg.messageContent);
            jsonArray.put(jsonMsg);
        }
        
        return jsonArray.toString(4); // Pretty print with 4 spaces
    }
    
    /**
     * Validates that message content does not exceed 250 characters.
     * @param content the message content to validate
     * @return success or failure message
     */
    public static String validateMessageLength(String content) {
        if (content == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        
        if (content.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = content.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    // Getters and Setters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageContent() { return messageContent; }
    public static List<Message> getSentMessages() { return sentMessages; }
    
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
}