package chatapp;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class ChatApp {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        LoginFeature security = new LoginFeature();
        
        // register user
        System.out.println("========================================");
        System.out.println("            WELCOME TO CHATAPP");
        System.out.println("========================================\n");
        
        System.out.print("Enter first name: ");
        if (!keyboard.hasNextLine()){
            return;
        }
        String FName = keyboard.nextLine();
        System.out.print("Enter last name: ");
        String lName = keyboard.nextLine();
        System.out.println();
        
        String chosenUser;
        do {
            System.out.print("Enter username (must contain _ and be ≤5 chars): ");
            chosenUser = keyboard.nextLine();
            if (!security.checkUserName(chosenUser))
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } while (!security.checkUserName(chosenUser));
        System.out.println("Username successfully captured.\n");
        
        String chosenPass;
        do {
            System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
            chosenPass = keyboard.nextLine();
            if (!security.checkPasswordComplexity(chosenPass))
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } while (!security.checkPasswordComplexity(chosenPass));
        System.out.println("Password successfully captured.\n");
        
        String mobileNum;
        do {
            System.out.print("Enter cell phone number (+27 then 9 digits): ");
            mobileNum = keyboard.nextLine();
            if (!security.checkCellPhoneNumber(mobileNum))
                System.out.println("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.");
        } while (!security.checkCellPhoneNumber(mobileNum));
        System.out.println("Cell phone number successfully added.\n");
        
        String regMessage = security.registerUser(chosenUser, chosenPass, FName, lName, mobileNum);
        System.out.println(regMessage);
        
        // login
        System.out.println("\n========================================");
        System.out.println("              LOGIN SECTION");
        System.out.println("========================================\n");
        boolean logged = false;
        while (!logged) {
            System.out.print("Enter username: ");
            String u = keyboard.nextLine();
            System.out.print("Enter password: ");
            String p = keyboard.nextLine();
            logged = security.loginUser(u, p);
            if (logged)
                System.out.println("\n" + security.returnLoginStatus(true, FName, lName));
            else
                System.out.println("Username or password incorrect, please try again.\n");
        }
        
        // Part 2 and Part 3 menu
        System.out.println("\n========================================");
        System.out.println("          Welcome to ChatApp");
        System.out.println("========================================");
        
        // parallel arrays for storing message data
        ArrayList<String> sentMsgs = new ArrayList<>();
        ArrayList<String> storedMsgs = new ArrayList<>();
        ArrayList<String> discardedMsgs = new ArrayList<>();
        ArrayList<String> msgIds = new ArrayList<>();
        ArrayList<String> msgHashes = new ArrayList<>();
        ArrayList<String> msgRecipients = new ArrayList<>();
        ArrayList<String> msgTexts = new ArrayList<>();
        
        int totalSentCount = 0;
        boolean wantExit = false;
        
        while (!wantExit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages (Part 3)");
            System.out.print("Choose an option: ");
            int choice = keyboard.nextInt();
            keyboard.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("How many messages do you want to send? ");
                    int howMany = keyboard.nextInt();
                    keyboard.nextLine();
                    
                    for (int i = 1; i <= howMany; i++) {
                        System.out.println("\n--- Message " + i + " ---");
                        
                        String receiver;
                        do {
                            System.out.print("Recipient cell number (+27 then 9 digits): ");
                            receiver = keyboard.nextLine();
                            if (!security.checkCellPhoneNumber(receiver))
                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        } while (!security.checkCellPhoneNumber(receiver));
                        
                        String text;
                        while (true) {
                            System.out.print("Message (max 250 characters): ");
                            text = keyboard.nextLine();
                            if (text.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                int excess = text.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                            }
                        }
                        
                        Message msg = new Message(i, receiver, text);
                        
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Choice: ");
                        int action = keyboard.nextInt();
                        keyboard.nextLine();
                        
                        switch (action) {
                            case 1:
                                System.out.println("Message successfully sent");
                                totalSentCount++;
                                sentMsgs.add(msg.getText());
                                msgIds.add(msg.getId());
                                msgHashes.add(msg.getHash());
                                msgRecipients.add(msg.getReceiver());
                                msgTexts.add(msg.getText());
                                break;
                            case 2:
                                System.out.println("Press 0 to delete the message");
                                discardedMsgs.add(msg.getText());
                                break;
                            case 3:
                                System.out.println("Message successfully stored");
                                msg.saveToFile();
                                storedMsgs.add(msg.getText());
                                msgIds.add(msg.getId());
                                msgHashes.add(msg.getHash());
                                msgRecipients.add(msg.getReceiver());
                                msgTexts.add(msg.getText());
                                break;
                            default:
                                System.out.println("Invalid option – message disregarded.");
                        }
                        
                        System.out.println("\nMessage Details");
                        System.out.println("Message ID: " + msg.getId());
                        System.out.println("Message Hash: " + msg.getHash());
                        System.out.println("Recipient: " + msg.getReceiver());
                        System.out.println("Message: " + msg.getText());
                    }
                    
                    System.out.println("\nTotal messages sent: " + totalSentCount);
                    break;
                    
                case 2:
                    System.out.println("Coming Soon");
                    break;
                    
                case 3:
                    wantExit = true;
                    System.out.println("Goodbye!");
                    break;
                    
                case 4:
                    boolean back = false;
                    while (!back) {
                        System.out.println("\n--- Stored Messages Menu ---");
                        System.out.println("a. Show sender and recipient of all stored messages");
                        System.out.println("b. Show the longest stored message");
                        System.out.println("c. Find message by ID");
                        System.out.println("d. Find all messages for a recipient");
                        System.out.println("e. Delete a message by hash");
                        System.out.println("f. Full report (hash, recipient, message)");
                        System.out.println("g. Back to main menu");
                        System.out.print("Choose: ");
                        String opt = keyboard.nextLine();
                        
                        switch (opt) {
                            case "a":
                                System.out.println("\nSender and Recipient:");
                                for (int i = 0; i < storedMsgs.size(); i++) {
                                    System.out.println("Sender: " + FName + " " + lName + " | Recipient: " + msgRecipients.get(i));
                                }
                                break;
                            case "b":
                                String longest = "";
                                for (String s : storedMsgs) {
                                    if (s.length() > longest.length()) longest = s;
                                }
                                System.out.println("\nLongest stored message: " + longest);
                                break;
                            case "c":
                                System.out.print("Enter Message ID: ");
                                String searchId = keyboard.nextLine();
                                boolean found = false;
                                for (int i = 0; i < msgIds.size(); i++) {
                                    if (msgIds.get(i).equals(searchId)) {
                                        System.out.println("Recipient: " + msgRecipients.get(i));
                                        System.out.println("Message: " + msgTexts.get(i));
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) System.out.println("ID not found.");
                                break;
                            case "d":
                                System.out.print("Enter recipient number: ");
                                String targetRecip = keyboard.nextLine();
                                System.out.println("\nMessages to " + targetRecip + ":");
                                for (int i = 0; i < msgRecipients.size(); i++) {
                                    if (msgRecipients.get(i).equals(targetRecip)) {
                                        System.out.println("- " + msgTexts.get(i));
                                    }
                                }
                                break;
                            case "e":
                                System.out.print("Enter Message Hash to delete: ");
                                String delHash = keyboard.nextLine();
                                boolean removed = false;
                                for (int i = 0; i < msgHashes.size(); i++) {
                                    if (msgHashes.get(i).equals(delHash)) {
                                        msgHashes.remove(i);
                                        msgIds.remove(i);
                                        msgRecipients.remove(i);
                                        msgTexts.remove(i);
                                        storedMsgs.remove(i);
                                        System.out.println("Message successfully deleted.");
                                        removed = true;
                                        break;
                                    }
                                }
                                if (!removed) System.out.println("Hash not found.");
                                break;
                            case "f":
                                System.out.println("\n--- Full Report ---");
                                System.out.println("Hash\t\tRecipient\t\tMessage");
                                for (int i = 0; i < storedMsgs.size(); i++) {
                                    System.out.println(msgHashes.get(i) + "\t" + msgRecipients.get(i) + "\t" + storedMsgs.get(i));
                                }
                                break;
                            case "g":
                                back = true;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid menu choice.");
            }
        }
        keyboard.close();
    }
}