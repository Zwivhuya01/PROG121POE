

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import poe.part_1.Message;
import poe.part_1.MessageManager;


/**
 *
 * @author  Zwivhuya
 */

public class MessageManagerTest {
    
    //  Sent Messages correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {
        MessageManager.populateWithTestData();
        List<Message> sentMessages = MessageManager.getSentMessages();
        
        boolean hasCake = false;
        boolean hasDinner = false;
        
        for (Message msg : sentMessages) {
            if (msg.getMessageContent().equals("Did you get the cake?")) hasCake = true;
            if (msg.getMessageContent().equals("It is dinner time!")) hasDinner = true;
        }
        
        assertTrue(hasCake, "Should contain 'Did you get the cake?'");
        assertTrue(hasDinner, "Should contain 'It is dinner time!'");
    }
    
    //Display longest message
    @Test
    public void testDisplayLongestMessage() {
        MessageManager.populateWithTestData();
        List<Message> stored = MessageManager.getStoredMessages();
        
        Message longest = stored.get(0);
        for (Message msg : stored) {
            if (msg.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = msg;
            }
        }
        
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, longest.getMessageContent());
    }
    
    // Search for message ID
    @Test
    public void testSearchByMessageID() {
        MessageManager.populateWithTestData();
        
        // Search for message 4 (developer entry)
        String searchID = "MSG004";
        String expected = "It is dinner time!";
        
        List<Message> sent = MessageManager.getSentMessages();
        String actual = "";
        for (Message msg : sent) {
            if (msg.getMessageID().equals(searchID)) {
                actual = msg.getMessageContent();
            }
        }
        
        assertEquals(expected, actual);
    }
    
    //  Search all messages for recipient +27838884567
    @Test
    public void testSearchByRecipient() {
        MessageManager.populateWithTestData();
        String recipient = "+27838884567";
        
        List<Message> stored = MessageManager.getStoredMessages();
        List<String> messages = new java.util.ArrayList<>();
        
        for (Message msg : stored) {
            if (msg.getRecipientCell().equals(recipient)) {
                messages.add(msg.getMessageContent());
            }
        }
        
        assertEquals(2, messages.size());
        assertTrue(messages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(messages.contains("Ok, I am leaving without you."));
    }
    
    //  Delete message using message hash
    @Test
    public void testDeleteMessageByHash() {
        MessageManager.populateWithTestData();
        List<Message> stored = MessageManager.getStoredMessages();
        
        // Get hash of message 2
        String hashToDelete = "";
        for (Message msg : stored) {
            if (msg.getMessageContent().contains("Where are you?")) {
                hashToDelete = msg.getMessageHash();
            }
        }
        
        boolean deleted = MessageManager.deleteMessageByHash(hashToDelete);
        assertTrue(deleted);
    }
    
    //  Report shows hash, recipient, message
    @Test
    public void testReportFormat() {
        MessageManager.populateWithTestData();
        List<Message> sent = MessageManager.getSentMessages();
        
        for (Message msg : sent) {
            assertNotNull(msg.getMessageHash());
            assertNotNull(msg.getRecipientCell());
            assertNotNull(msg.getMessageContent());
            assertTrue(msg.getMessageHash().contains(":"));
        }
        
        assertTrue(sent.size() > 0);
    }
}