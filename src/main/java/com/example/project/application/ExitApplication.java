package com.example.project.application;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class ExitApplication implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 42;
    }
}
