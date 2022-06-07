package com.example.project.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
class UpbitConfigTest {
    @Autowired
    private UpbitConfig upbitConfig;

    @Test
    void getServerUrl() {
        assertThat(upbitConfig.getServerUrl(), is("https://api.upbit.com"));
    }

    @Test
    void getAccessKey() {
        assertThat(upbitConfig.getAccessKey(), is("ul4mG5syi3fo4EUWoxxbpGSxKzj3r94Y3Wcb3wx1"));
    }

    @Test
    void getSecretKey() {
        assertThat(upbitConfig.getSecretKey(), is("7Rxgz97yvm8wFe6H8mmFVYnkKmQfttB0Va8Wryp7"));
    }
}