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
    
    private static final int MAX_MESSAGE_LENGTH = 250;
    private static final int HASH_PREFIX_LENGTH = 5;
    
    private String messageID;
    private String messageHash;
    private String recipientCell;
    private String messageContent;
    
    private static final List<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    
  
    public Message(String messageID, String messageHash, String recipientCell, String messageContent) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
    }
    
    
    //  Validates that message ID does not exceed ten characters.
     
    
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
    
    /**
     * Validates the recipient cell phone number format.
     * Must start with +27 and contain only digits after the code.
     */
    public String checkRecipientCell() {
        if (recipientCell == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
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
    
   
    public String createMessageHash() {
        if (messageContent == null || messageContent.isEmpty()) {
            return "00:0:";
        }
        
        int length = messageContent.length();
        int vowelCount = countVowels(messageContent);
        
        // Get first 5 characters of content for the hash (or entire content if shorter)
        String contentPrefix = messageContent.length() > HASH_PREFIX_LENGTH ? 
                               messageContent.substring(0, HASH_PREFIX_LENGTH) : messageContent;
        
        return String.format("%02d:%d:%s", length, vowelCount, contentPrefix);
    }
    
    //Counts the number of vowels in a string.
     
 
    private int countVowels(String text) {
        int vowelCount = 0;
        for (char c : text.toLowerCase().toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowelCount++;
            }
        }
        return vowelCount;
    }
    
    // Processes the message based on user's choice (send, store, or disregard).
     
  
    public String processMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                sentMessages.add(this);
                return "Message successfully stored.";
            case 3:
                return "Message disregarded.";
            default:
                return "Invalid choice. Message not processed.";
        }
    }
    
   
    public static String printAllMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent or stored yet.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("\n=== All Sent/Stored Messages ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            result.append(String.format("%d. ID: %s | Hash: %s | Recipient: %s | Content: %s%n",
                    i + 1, msg.messageID, msg.messageHash, msg.recipientCell, msg.messageContent));
        }
        return result.toString();
    }
    
    // Returns the total number of messages sent.
     
   
    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }
    
    // Converts all stored messages to JSON format.
    
     
    public static String toJSON() {
        JSONArray jsonArray = new JSONArray();
        
        for (Message msg : sentMessages) {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("messageID", msg.messageID);
            jsonMsg.put("messageHash", msg.messageHash);
            jsonMsg.put("recipientCell", msg.recipientCell);
            jsonMsg.put("messageContent", msg.messageContent);
            jsonArray.put(jsonMsg);
        }
        
        return jsonArray.toString(4);
    }
    
    
    // Validates that message content does not exceed maximum length.
    
    public static String validateMessageLength(String content) {
        if (content == null) {
            return String.format("Message exceeds %d characters by %d; please reduce the size.", 
                    MAX_MESSAGE_LENGTH, MAX_MESSAGE_LENGTH);
        }
        
        if (content.length() <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        } else {
            int excess = content.length() - MAX_MESSAGE_LENGTH;
            return String.format("Message exceeds %d characters by %d; please reduce the size.", 
                    MAX_MESSAGE_LENGTH, excess);
        }
    }
    
    // Getters and Setters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageContent() { return messageContent; }
    public static List<Message> getSentMessages() { return new ArrayList<>(sentMessages); }
    
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
}