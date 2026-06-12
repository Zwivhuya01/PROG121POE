import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poe.part_1.Message;

/**
 * 
 * @author Zwivhuya
 */
public class MessageTest {
    
    private Message message1;
    private Message message2;
    
    @BeforeEach
    public void setUp() {
       
        message1 = new Message("MSG001", "", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message2 = new Message("MSG002", "", "08575975889", "Hi Keegan, did you receive the payment?");
    }
    
   
    @Test
    @DisplayName("Test Message ID Validation - Success")
    public void testCheckMessageID_Success() {
        // Valid ID - exactly 10 characters
        Message validMessage = new Message("1234567890", "", "+27718693002", "Test message");
        assertTrue(validMessage.checkMessageID(), "Message ID with 10 characters should be valid");
        
        // Valid ID - less than 10 characters
        Message validMessage2 = new Message("MSG001", "", "+27718693002", "Test message");
        assertTrue(validMessage2.checkMessageID(), "Message ID with less than 10 characters should be valid");
    }
    
    @Test
    @DisplayName("Test Message ID Validation - Failure")
    public void testCheckMessageID_Failure() {
        // Invalid ID more than 10 characters
        Message invalidMessage = new Message("12345678901", "", "+27718693002", "Test message");
        assertFalse(invalidMessage.checkMessageID(), "Message ID with 11 characters should be invalid");
        
        // Null ID
        Message nullIdMessage = new Message(null, "", "+27718693002", "Test message");
        assertFalse(nullIdMessage.checkMessageID(), "Null message ID should be invalid");
    }
    
    // Recipient cell number should start with international code and have no more than ten digits after code
    
    @Test
    @DisplayName("Test Recipient Cell Validation - Success")
    public void testCheckRecipientCell_Success() {
        // Valid South African number
        Message validRecipient = new Message("MSG001", "", "+27718693002", "Test message");
        String result = validRecipient.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
        
        // Valid number exactly 10 digits after code
        Message validRecipient2 = new Message("MSG002", "", "+27123456789", "Test message");
        String result2 = validRecipient2.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result2);
    }
    
    @Test
    @DisplayName("Test Recipient Cell Validation - Failure")
    public void testCheckRecipientCell_Failure() {
        // Invalid - missing international code
        Message invalidRecipient = new Message("MSG001", "", "08575975889", "Test message");
        String result = invalidRecipient.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
        
        // Invalid - more than 10 digits after code
        Message invalidRecipient2 = new Message("MSG002", "", "+2712345678901", "Test message");
        String result2 = invalidRecipient2.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result2);
        
        // Null recipient
        Message nullRecipient = new Message("MSG003", "", null, "Test message");
        String result3 = nullRecipient.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result3);
    }
    
    // Test Message Hash Creation
    @Test
    @DisplayName("Test Create Message Hash")
    public void testCreateMessageHash() {
        String content = "Hello World";
        Message testMessage = new Message("MSG001", "", "+27718693002", content);
        String hash = testMessage.createMessageHash();
        
        assertEquals("11:3:Hello", hash);
        
        // Test with shorter message
        Message shortMessage = new Message("MSG002", "", "+27718693002", "Hi");
        String shortHash = shortMessage.createMessageHash();
        assertEquals("02:1:Hi", shortHash);
    }
    
    // Test Message Length Validation (250 character limit)
     
    @Test
    @DisplayName("Test Message Length Validation Success")
    public void testValidateMessageLength_Success() {
        String validMessage = "This is a short message under 250 characters";
        String result = Message.validateMessageLength(validMessage);
        assertEquals("Message ready to send.", result);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            sb.append("a");
        }
        String exactMessage = sb.toString();
        String result2 = Message.validateMessageLength(exactMessage);
        assertEquals("Message ready to send.", result2);
    }
    
    @Test
    @DisplayName("Test Message Length Validation - Failure")
    public void testValidateMessageLength_Failure() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            sb.append("a");
        }
        String longMessage = sb.toString();
        String result = Message.validateMessageLength(longMessage);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
        
        // Null message
        String result2 = Message.validateMessageLength(null);
        assertEquals("Message exceeds 250 characters by 250; please reduce the size.", result2);
    }
    
    
    @Test
    @DisplayName("Test Send Message")
    public void testSendMessage() {
        String result = message1.processMessage(1); // Send
        assertEquals("Message successfully sent.", result);
        assertEquals(1, Message.getTotalMessagesSent(), "Total messages sent should be 1");
        assertEquals(1, Message.getSentMessages().size(), "Sent messages list should contain 1 message");
    }
    
    @Test
    @DisplayName("Test Disregard Message")
    public void testDisregardMessage() {
        String result = message1.processMessage(3); // Disregard
        assertEquals("Message disregarded.", result);
        assertEquals(0, Message.getTotalMessagesSent(), "Total messages sent should still be 0");
       
    }
    
    @Test
    @DisplayName("Test Store Message")
    public void testStoreMessage() {
        String result = message1.processMessage(2); // Store
        assertEquals("Message successfully stored.", result);
        assertEquals(0, Message.getTotalMessagesSent(), "Total messages sent should still be 0 (stored != sent)");
        assertEquals(1, Message.getSentMessages().size(), "Stored messages list should contain 1 message");
    }
    
    @Test
    @DisplayName("Test Invalid Choice")
    public void testInvalidChoice() {
        String result = message1.processMessage(99);
        assertEquals("Invalid choice. Message not processed.", result);
        assertEquals(0, Message.getTotalMessagesSent());
    }
    
    // Test Print Messages functionality
     
    @Test
    @DisplayName("Test Print Messages")
    public void testPrintMessages() {
       
        
        // Create a new message and store it
        Message testMsg = new Message("MSG001", "", "+27718693002", "Test content");
        testMsg.setMessageHash(testMsg.createMessageHash());
        testMsg.processMessage(2); // Store
        
        String result = Message.printAllMessages();
        assertTrue(result.contains("ID: MSG001"));
        assertTrue(result.contains("Recipient: +27718693002"));
        assertTrue(result.contains("Content: Test content"));
    }
    
    @Test
    @DisplayName("Test Print Messages When Empty - Alternative Approach")
    public void testPrintMessagesEmpty() {
      
        String result = Message.printAllMessages();
       
        assertNotNull(result);
    }
    
    // Test Return Total Messages functionality
     
    @Test
    @DisplayName("Test Return Total Messages")
    public void testReturnTotalMessages() {
        int initialTotal = Message.getTotalMessagesSent();
        
        message1.processMessage(1); // Send
        assertEquals(initialTotal + 1, Message.getTotalMessagesSent(), "After sending one message, total should increase by 1");
        
        message2.processMessage(1); // Send
        assertEquals(initialTotal + 2, Message.getTotalMessagesSent(), "After sending second message, total should increase by 2");
        
       
        Message message3 = new Message("MSG003", "", "+27718693002", "Third message");
        message3.processMessage(2); // Store
        assertEquals(initialTotal + 2, Message.getTotalMessagesSent(), "Storing should not increase sent count");
    }
    
    // Test JSON Storage functionality
     
    @Test
    @DisplayName("Test Store Messages to JSON")
    public void testStoreMessagesToJSON() {
        // Create messages with proper hashes
        message1.setMessageHash(message1.createMessageHash());
        message1.processMessage(1); // Send
        
        message2.setMessageHash(message2.createMessageHash());
        message2.processMessage(2); // Store
        
        String jsonOutput = Message.toJSON();
        
        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("MSG001"));
        assertTrue(jsonOutput.contains("+27718693002"));
        assertTrue(jsonOutput.contains("Hi Mike, can you join us for dinner tonight?"));
        assertTrue(jsonOutput.contains("MSG002"));
        assertTrue(jsonOutput.contains("08575975889"));
    }
    
  
     
    @Test
    @DisplayName("Integration Test - Test Data Message 1")
    public void testIntegrationTestDataMessage1() {
        // Test Data for Message 1
        Message testMsg1 = new Message("AUTO001", "", "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        testMsg1.setMessageHash(testMsg1.createMessageHash());
        
        // Validate message length
        String lengthValidation = Message.validateMessageLength(testMsg1.getMessageContent());
        assertEquals("Message ready to send.", lengthValidation);
        
        // Validate recipient
        String recipientValidation = testMsg1.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", recipientValidation);
        
        // Create message hash
        String hash = testMsg1.createMessageHash();
        assertNotNull(hash);
        assertTrue(hash.length() > 0);
        
        // Send message
        String sendResult = testMsg1.processMessage(1);
        assertEquals("Message successfully sent.", sendResult);
        
        // Verify total messages sent
        assertTrue(Message.getTotalMessagesSent() >= 1);
    }
    
    @Test
    @DisplayName("Integration Test - Test Data Message 2 (Discard)")
    public void testIntegrationTestDataMessage2() {
        // Test Data for Message 2
        Message testMsg2 = new Message("AUTO002", "", "08575975889", "Hi Keegan, did you receive the payment?");
        testMsg2.setMessageHash(testMsg2.createMessageHash());
        
        // Validate message length
        String lengthValidation = Message.validateMessageLength(testMsg2.getMessageContent());
        assertEquals("Message ready to send.", lengthValidation);
        
        // Validate recipient - this should fail (no international code)
        String recipientValidation = testMsg2.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", recipientValidation);
        
        // Create message hash
        String hash = testMsg2.createMessageHash();
        assertNotNull(hash);
        
        // Discard message
        String discardResult = testMsg2.processMessage(3);
        assertEquals("Message disregarded.", discardResult);
    }
    
    
    
    @Test
    @DisplayName("Test Message Hash Creation in Loop")
    public void testMessageHashCreationInLoop() {
        String[] testMessages = {
            "Hello World",
            "This is a test message",
            "Another message for testing",
            "Short",
            "Message with many vowels a e i o u"
        };
        
        for (String content : testMessages) {
            Message msg = new Message("TEST001", "", "+27718693002", content);
            String hash = msg.createMessageHash();
            
            // Verify hash format
            assertTrue(hash.matches("\\d{2}:\\d+:.*"), 
                      "Hash '" + hash + "' does not match expected format for message: " + content);
            
            // Verify length component
            int colonIndex = hash.indexOf(":");
            int reportedLength = Integer.parseInt(hash.substring(0, colonIndex));
            assertEquals(content.length(), reportedLength, 
                        "Reported length doesn't match actual length for message: " + content);
        }
    }
    
    @Test
    @DisplayName("Test Message Hash - Vowel Counting")
    public void testMessageHashVowelCounting() {
        Message msg = new Message("TEST", "", "+27718693002", "AEIOUaeiou");
        String hash = msg.createMessageHash();
        // 10 characters, 10 vowels (both cases)
        assertEquals("10:10:AEIOU", hash);
    }
    
    @Test
    @DisplayName("Test Message Hash - Empty Content")
    public void testMessageHashEmptyContent() {
        Message msg = new Message("TEST", "", "+27718693002", "");
        String hash = msg.createMessageHash();
        assertEquals("00:0:", hash);
    }
    
    @Test
    @DisplayName("Test Message Hash - Null Content")
    public void testMessageHashNullContent() {
        Message msg = new Message("TEST", "", "+27718693002", null);
        String hash = msg.createMessageHash();
        assertEquals("00:0:", hash);
    }
}