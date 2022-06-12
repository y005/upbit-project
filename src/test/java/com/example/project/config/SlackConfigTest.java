package com.example.project.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SlackConfigTest {
    @Autowired
    private SlackConfig slackConfig;

    @Test
    void getUrl() {
        assertThat(slackConfig.getUrl(), is("https://hooks.slack.com/services/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"));
    }
}