package org.a3.mandarin.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MandarinBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MandarinBackApplication.class, args);
    }

}
