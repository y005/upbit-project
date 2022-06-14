package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackChannelMessage {
    private String channel;
    private String text;

    public SlackChannelMessage(Object data) {
        this.text = data.toString();
    }
}