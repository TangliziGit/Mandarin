package org.a3.mandarin.runner;

import org.a3.mandarin.back.MandarinBackApplication;
import org.a3.mandarin.common.MandarinCommonApplication;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.front.MandarinFrontApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MandarinRunnerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext=SpringApplication.run(new Class[]{
                MandarinCommonApplication.class,
                MandarinBackApplication.class,
                MandarinFrontApplication.class
        }, args);

        Initializer initializer=new Initializer(applicationContext);
        // initializer.init();
        RoleUtil.initRoles();
    }
}
