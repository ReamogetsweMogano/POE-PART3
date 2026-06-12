package chatapp;

import java.util.regex.Pattern;

public class LoginFeature {
    
    private String savedUser;
    private String savedPass;
    private String savedFirst;
    private String savedLast;
    private String savedPhone;
    
    public boolean checkUserName(String s) {
        if (s == null) return false;
        return s.contains("_") && s.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String s) {
        if (s == null) return false;
        boolean lenOk = s.length() >= 8;
        boolean hasCap = s.matches(".*[A-Z].*");
        boolean hasNum = s.matches(".*[0-9].*");
        boolean hasSpec = s.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?~`].*");
        return lenOk && hasCap && hasNum && hasSpec;
    }
    
    // regex pattern from https://regex101.com/
    public boolean checkCellPhoneNumber(String s) {
        if (s == null) return false;
        return Pattern.matches("^\\+27[0-9]{9}$", s);
    }
    
    public String registerUser(String u, String p, String f, String l, String ph) {
        StringBuilder msg = new StringBuilder();
        boolean valid = true;
        
        if (!checkUserName(u)) {
            msg.append("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n");
            valid = false;
        } else {
            msg.append("Username successfully captured.\n");
        }
        
        if (!checkPasswordComplexity(p)) {
            msg.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            valid = false;
        } else {
            msg.append("Password successfully captured.\n");
        }
        
        if (!checkCellPhoneNumber(ph)) {
            msg.append("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.\n");
            valid = false;
        } else {
            msg.append("Cell phone number successfully added.\n");
        }
        
        if (valid) {
            savedUser = u;
            savedPass = p;
            savedFirst = f;
            savedLast = l;
            savedPhone = ph;
            return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nUser registered successfully.";
        } else {
            return msg.toString().trim();
        }
    }
    
    public boolean loginUser(String u, String p) {
        if (u == null || p == null) return false;
        return u.equals(savedUser) && p.equals(savedPass);
    }
    
    public String returnLoginStatus(boolean ok, String f, String l) {
        if (ok)
            return "Welcome " + f + ", " + l + " it is great to see you again.";
        else
            return "Username or password incorrect, please try again.";
    }
}