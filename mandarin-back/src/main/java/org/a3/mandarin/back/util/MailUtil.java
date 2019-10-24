package org.a3.mandarin.back.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.time.Duration;
import java.util.List;
import java.time.Instant;

@Component
public class MailUtil {
    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private BorrowingHistoryRepository borrowingHistoryRepository;

    // @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
    public void sendAlert() {
        List<BorrowingHistory> borrowingHistory= borrowingHistoryRepository.findNoReturnHistory();
        Instant now = Instant.now();
        for (BorrowingHistory history : borrowingHistory) {
            Instant start = history.getBorrowingStartTime();
            Instant limitation = start.plus(Duration.ofMinutes(1));
            if (now.isAfter(limitation)){
                User user = history.getReader();

                // TODO: comment this code in the future
                if (user.getEmail().contains("mandarin")){
                    logger.info("filtered mandarin testing account: " + user.getName());
                    continue;
                }
                logger.info("alert mail will send to " + user.getName());

                sendMail(user.getEmail(),
                        "Mandarin Library: Your book is about to expire",
                        "The book you borrowed is due in three days.\n" +
                                "PLease don't forget to return it back or you will be fine 1 yuan per day");
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