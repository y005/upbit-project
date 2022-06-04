package com.example.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ApplicationTerminator {

    private final ApplicationContext applicationContext;
    private final ExitCodeGenerator exitCodeGenerator;

    public void exit() {
        SpringApplication.exit(applicationContext, exitCodeGenerator);
    }
}