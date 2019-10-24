package org.a3.mandarin.back.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.BorrowingHistory;
import org.a3.mandarin.common.entity.User;

import javax.annotation.Resource;
import java.util.List;
import java.time.Instant;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private BorrowingHistoryRepository borrowingHistoryRepository;

    //@Scheduled(fixedRate=30000)
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendAlter() {
        List<BorrowingHistory> borrowingHistory= borrowingHistoryRepository.findNoReturnHistory();
        if(!borrowingHistory.isEmpty()) {
            long now = Instant.now().getEpochSecond();
            for (int i = 0; i < borrowingHistory.size(); i++) {
                long borrowingStartTime = borrowingHistory.get(i).getBorrowingStartTime().getEpochSecond();
                if(now - borrowingStartTime >= 27 * 24 * 60 * 60 && now - borrowingStartTime <= 28 * 24 * 60 * 60) {
                User user = borrowingHistory.get(i).getReader();
                sendMail(user.getEmail(),
                        "IMPORTANT! This is a Alter mail form Mandarin!",
                        "The book you borrowed is due in three days. PLease don't forget to return it back or you will be fine 1 yuan per day");
                }
            }
        }
    }

    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setCc(this.from);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}