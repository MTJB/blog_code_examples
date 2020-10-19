package com.mtjb.examples.services;

import org.h2.tools.Console;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Configuration
public class H2ConsoleConfig {

    static private boolean h2Started;

    @PostConstruct
    public void init() {
        if (!h2Started) {
            h2_Console();
        }
    }

    @Profile({"test", "h2-console"})
    public Thread h2_Console() {
        h2Started = true;
        Thread thread = new Thread(() -> {
            try {
                Console.main("-web");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.setName("h2-console runner");
        thread.start();
        return thread;
    }
}