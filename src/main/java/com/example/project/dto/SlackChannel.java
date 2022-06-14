package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackChannel {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
