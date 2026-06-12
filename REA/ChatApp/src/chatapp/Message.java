package chatapp;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private String uid;
    private int seq;
    private String target;
    private String body;
    private String hash;
    
    public Message(int n, String to, String txt) {
        seq = n;
        target = to;
        body = txt;
        uid = generateId();
        hash = buildHash();
    }
    
    // test constructor (fixed ID)
    public Message(int n, String to, String txt, String fixedId) {
        seq = n;
        target = to;
        body = txt;
        uid = fixedId;
        hash = buildHash();
    }
    
    private String generateId() {
        Random r = new Random();
        long val = 1000000000L + (long)(r.nextDouble() * 9000000000L);
        return Long.toString(val);
    }
    
    private String cleanWord(String w) {
        return w.replaceAll("[^A-Za-z]", "");
    }
    
    public String buildHash() {
        String start = uid.substring(0, 2);
        String[] parts = body.trim().split("\\s+");
        String first = cleanWord(parts[0]).toUpperCase();
        String last = cleanWord(parts[parts.length - 1]).toUpperCase();
        return start + ":" + seq + ":" + first + last;
    }
    
    public boolean idOk() {
        return uid != null && uid.length() <= 10;
    }
    
    public String checkTarget() {
        if (target == null || !target.matches("^\\+27[0-9]{9}$"))
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }
    
    public void saveToFile() {
        try (FileWriter fw = new FileWriter("chatstore.json", true)) {
            fw.write("{\"id\":\"" + uid + "\", \"hash\":\"" + hash + "\", \"to\":\"" + target + "\", \"msg\":\"" + body + "\"}\n");
        } catch (IOException e) {
            System.out.println("JSON save failed.");
        }
    }
    
    public String getId() { return uid; }
    public int getSeq() { return seq; }
    public String getReceiver() { return target; }
    public String getText() { return body; }
    public String getHash() { return hash; }
}