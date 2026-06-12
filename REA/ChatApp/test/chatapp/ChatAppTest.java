package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ChatAppTest {
    
    private ArrayList<String> sentArray;
    private ArrayList<String> storedArray;
    private ArrayList<String> idArray;
    private ArrayList<String> hashArray;
    private ArrayList<String> recipArray;
    private ArrayList<String> textArray;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setup() {
        sentArray = new ArrayList<>();
        storedArray = new ArrayList<>();
        idArray = new ArrayList<>();
        hashArray = new ArrayList<>();
        recipArray = new ArrayList<>();
        textArray = new ArrayList<>();
        
        // Message 1 (sent)
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        sentArray.add(m1.getText());
        idArray.add(m1.getId());
        hashArray.add(m1.getHash());
        recipArray.add(m1.getReceiver());
        textArray.add(m1.getText());
        
        // Message 2 (stored)
        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        storedArray.add(m2.getText());
        idArray.add(m2.getId());
        hashArray.add(m2.getHash());
        recipArray.add(m2.getReceiver());
        textArray.add(m2.getText());
        
        // Message 3 (disregarded) – not added
        
        // Message 4 (sent) – recipient is 0838884567 (no +27)
        Message m4 = new Message(4, "0838884567", "It is dinner time!");
        sentArray.add(m4.getText());
        idArray.add(m4.getId());
        hashArray.add(m4.getHash());
        recipArray.add(m4.getReceiver());
        textArray.add(m4.getText());
        
        // Message 5 (stored)
        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        storedArray.add(m5.getText());
        idArray.add(m5.getId());
        hashArray.add(m5.getHash());
        recipArray.add(m5.getReceiver());
        textArray.add(m5.getText());
    }

    @After
    public void tearDown() throws Exception {
    }
    
    // test 1: sent messages array contains correct texts
    @Test
    public void testSentArrayContent() {
        assertEquals(2, sentArray.size());
        assertTrue(sentArray.contains("Did you get the cake?"));
        assertTrue(sentArray.contains("It is dinner time!"));
    }
    
    // test 2: longest stored message
    @Test
    public void testLongestStoredMessage() {
        String longest = "";
        for (String s : storedArray) {
            if (s.length() > longest.length()) longest = s;
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }
    
    // test 3: search by message ID (message 4)
    @Test
    public void testSearchById() {
        String targetId = idArray.get(2); // index of message 4
        String foundRecip = "";
        String foundText = "";
        for (int i = 0; i < idArray.size(); i++) {
            if (idArray.get(i).equals(targetId)) {
                foundRecip = recipArray.get(i);
                foundText = textArray.get(i);
                break;
            }
        }
        assertEquals("0838884567", foundRecip);
        assertEquals("It is dinner time!", foundText);
    }
    
    // test 4: search by recipient +27838884567 (finds messages 2 and 5)
    @Test
    public void testSearchByRecipient() {
        String target = "+27838884567";
        ArrayList<String> matches = new ArrayList<>();
        for (int i = 0; i < recipArray.size(); i++) {
            if (recipArray.get(i).equals(target)) {
                matches.add(textArray.get(i));
            }
        }
        assertEquals(2, matches.size());
        assertTrue(matches.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(matches.contains("Ok, I am leaving without you."));
    }
    
    // test 5: delete a message by hash (delete message 2 – stored)
    @Test
    public void testDeleteByHash() {
        String targetHash = hashArray.get(1); // message 2 hash
        String targetText = textArray.get(1);
        int oldSize = storedArray.size();
        
        // remove from combined arrays
        for (int i = 0; i < hashArray.size(); i++) {
            if (hashArray.get(i).equals(targetHash)) {
                hashArray.remove(i);
                idArray.remove(i);
                recipArray.remove(i);
                textArray.remove(i);
                break;
            }
        }
        // remove from storedArray by matching text
        for (int i = 0; i < storedArray.size(); i++) {
            if (storedArray.get(i).equals(targetText)) {
                storedArray.remove(i);
                break;
            }
        }
        assertEquals(oldSize - 1, storedArray.size());
        assertFalse(storedArray.contains(targetText));
    }
    
    // test 6: full report contains required data
    @Test
    public void testDisplayReport() {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < storedArray.size(); i++) {
            String msg = storedArray.get(i);
            int idx = textArray.indexOf(msg);
            if (idx != -1) {
                report.append(hashArray.get(idx)).append(" | ")
                      .append(recipArray.get(idx)).append(" | ")
                      .append(storedArray.get(i)).append("\n");
            }
        }
        String reportStr = report.toString();
        assertTrue(reportStr.contains("+27838884567"));
        assertTrue(reportStr.contains("Ok, I am leaving without you."));
    }

    /**
     * Test of main method, of class ChatApp.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ChatApp.main(args);
        
        
    }
}