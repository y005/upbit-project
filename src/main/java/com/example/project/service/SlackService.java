package com.example.project.service;

import com.example.project.client.SlackClient;
import com.example.project.config.SlackConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SlackService {
  private final SlackClient slackClient;
  private final SlackConfig slackConfig;

  public void sendAssetsInfo(Object data) {
    slackClient.sendMessageUsingOauth(data, slackConfig.getAssetsChannel());
  }

  public void sendOrderInfo(Object data) {
    slackClient.sendMessageUsingOauth(data, slackConfig.getOrderChannel());
  }

  public void sendBacktestInfo(Object data) {
    slackClient.sendMessageUsingOauth(data, slackConfig.getBacktestChannel());
  }
}
