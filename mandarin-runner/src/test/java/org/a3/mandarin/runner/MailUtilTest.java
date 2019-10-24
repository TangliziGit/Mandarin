package org.a3.mandarin.runner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.junit.Test;
import org.a3.mandarin.back.util.MailUtil;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//@SpringBootApplication
public class MailUtilTest extends MandarinRunnerApplicationTests {
    @Test
    public void testMailUtil( ) {
        //sendAlter();
    }
}
