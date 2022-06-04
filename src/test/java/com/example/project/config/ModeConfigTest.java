package com.example.project.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ModeConfigTest {
    @Autowired
    private ModeConfig modeConfig;

    @Test
    void getMode() {
        assertThat(modeConfig.getMode(), is("crawler"));
    }
}