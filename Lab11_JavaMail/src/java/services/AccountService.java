package services;

import java.util.*;
import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {
    
    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
//                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                
//                GmailService.sendMail(email, "Logged in", "Hi, someone has logged in", true);
  
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
                

                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public User resetPassword(String email, String path, String url){
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
                System.out.println("we are in the resetpassword. this is the email: " + email);
                String to = user.getEmail();
                String subject = "Password Reset";
                String template = path + "/emailtemplates/resetpassword.html";
                String uuid = UUID.randomUUID().toString();
                String link = url + "?uuid=" + uuid;
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
                
                user.setResetPasswordUuid(uuid);
                userDB.update(user);
                
            return user; 
        } catch (Exception e) {
        }
        
          return null;     
    }
    
    public boolean changePassword(String uuid, String password) {
       
       UserDB userDB = new UserDB();
        try {
            User user = userDB.getByUUID(uuid);
            System.out.println("This is hte user found by uuid: " + user);
            user.setPassword(password);
            user.setResetPasswordUuid(null);
            userDB.update(user);
             System.out.println("we changed the password! ");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
