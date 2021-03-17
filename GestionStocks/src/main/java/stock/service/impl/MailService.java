package stock.service.impl;

import java.security.Principal;

import stock.entity.Mail;


public interface MailService {
	
    public void sendEmail(Mail mail, Principal principal);
}
