package com.yjs;

import com.yjs.listener.ApplicationStartedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZizhujiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZizhujiApplication.class);
        application.addListeners(new ApplicationStartedEventListener());
        application.setHeadless(false);
        application.run(args);
    }
}
