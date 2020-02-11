package com.fengwk.support.uc.infrastructure.random.facade;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.Constants;
import com.fengwk.support.uc.domain.security.facade.RandomMessageSender;
import com.fengwk.support.uc.domain.security.model.EmailRandomMessage;
import com.fengwk.support.uc.domain.security.model.SMSRandomMessage;

/**
 * 
 * @author fengwk
 */
@Component
public class RandomMessageSenderImpl implements RandomMessageSender {

    @Autowired
    private volatile JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private volatile String from;
    
    private Executor executor = new ThreadPoolExecutor(
            Constants.AVAILABLE_PROCESSORS, 
            Constants.AVAILABLE_PROCESSORS, 
            0L, TimeUnit.MILLISECONDS, 
            new LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(), 
            new AbortPolicy());
    
    @Override
    public void sendToEmail(EmailRandomMessage emailMessage) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(from);
        simpleMessage.setTo(emailMessage.getTarget());
        simpleMessage.setSubject(emailMessage.getSubject());
        simpleMessage.setText(emailMessage.getText());
        executor.execute(() -> mailSender.send(simpleMessage));
    }

    @Override
    public void sendToSMS(SMSRandomMessage smsMessage) {
        throw new UnsupportedOperationException("尚不支持短信消息");
    }

}
