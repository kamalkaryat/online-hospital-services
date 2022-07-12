package com.k2dev.hospital.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.util.EmailDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private AccountService accountService;
    
	@Value("${spring.mail.username}") 
    private String sender;
 
	private int code= -1;
	
    public int sendMail(EmailDetails details){
        boolean isExists= accountService.isExists(details.getRecipient());
        if(!isExists)
        	return code;
        
    	try {
        	
            details.setSubject(setTitle());
            
            code= generateRandomNo();
            
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(msgBodyText(code));
            mailMessage.setSubject(details.getSubject());
 
            javaMailSender.send(mailMessage);
            return code;
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Exception while sending mail: {}", e.getMessage());
            return code;
        }
    }
    
    private final int generateRandomNo() {
    	return new Random().nextInt(900000) + 100000;
    }
    
    private final String msgBodyText(int code) {
    	String text= "<h3>Your verfication code is:</h2>"
    			+ "<p>"+code+"</p>";
    	return text;
    }
    
    private final String setTitle() {
    	String title= "<h1>Verification</h1>";
    	return title;
    }
}
