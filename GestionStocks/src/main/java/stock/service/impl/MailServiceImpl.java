package stock.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import stock.entity.Mail;

@Service("mailService")
public class MailServiceImpl implements MailService {
 
    @Autowired
    JavaMailSender mailSender;
 
    public void sendEmail(Mail mail, Principal principal) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
        	mimeMessage.setSubject(mail.getMailSubject());
            mimeMessage.setFrom(new InternetAddress(mail.getMailFrom(), principal.getName()));
            mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail.getMailTo()));
//            mimeMessage.setText(mail.getMailContent()); 
            mimeMessage.setContent("Votre commande est confirmée", "text/html"); 
            mailSender.send(mimeMessage);


            
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setSubject(mail.getMailSubject());
//            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), principal.getName()));
//            mimeMessageHelper.setTo(mail.getMailTo());
//            mimeMessageHelper.setText(mail.getMailContent());
 
//            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

 
}