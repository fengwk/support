package com.fengwk.support.uc.infrastructure.random.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.security.facade.RandomMessageFacade;
import com.fengwk.support.uc.domain.security.model.EmailRandomMessage;
import com.fengwk.support.uc.domain.security.model.SMSRandomMessage;

/**
 * 
 * @author fengwk
 */
@Component
public class MessageFacadeImpl implements RandomMessageFacade {

    @Autowired
    private volatile JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private volatile String from;
    
    @Override
    public void sendToEmail(EmailRandomMessage emailMessage) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(from);
        simpleMessage.setTo(emailMessage.getTarget());
        simpleMessage.setSubject(emailMessage.getSubject());
        simpleMessage.setText(emailMessage.getText());
        mailSender.send(simpleMessage);
    }

    @Override
    public void sendToSMS(SMSRandomMessage smsMessage) {
        throw ExceptionCodes.biz().create("尚不支持短信消息");
    }

}
