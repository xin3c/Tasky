package com.tasky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Tasky application.
 */
@EnableScheduling
@SpringBootApplication
public class TaskyApplication { //NOPMD - suppressed UseUtilityClass - TODO explain reason for suppression

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(TaskyApplication.class, args);
    }
}
