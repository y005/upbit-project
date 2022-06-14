package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SlackChannelApiResult {
    @JsonProperty("ok")
    private String ok;

    @JsonProperty("channels")
    private List<SlackChannel> channels;
}
