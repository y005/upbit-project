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
        assertThat(upbitConfig.getAccessKey(), is("w3WADLSdcVmRUMCtNAemNIxo7Ba1YPmZvlUVNuzn"));
    }

    @Test
    void getSecretKey() {
        assertThat(upbitConfig.getSecretKey(), is("Y4Ne1QeIY7Dr1RRMow5ARScNQFkz7ogo5wp0Qrq2"));
    }
}