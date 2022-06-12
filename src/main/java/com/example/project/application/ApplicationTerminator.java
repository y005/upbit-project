package com.example.project.application;

import lombok.AllArgsConstructor;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@AllArgsConstructor
public class ApplicationTerminator {
    private final ApplicationContext applicationContext;
    private final ExitCodeGenerator exitCodeGenerator;

    public void exit() {
        SpringApplication.exit(applicationContext, exitCodeGenerator);
    }
}