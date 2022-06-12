package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackMessage {
    private String text;

    public SlackMessage(Object data) {
        this.text = data.toString();
    }
}
