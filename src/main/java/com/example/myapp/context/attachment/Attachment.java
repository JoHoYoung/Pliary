package com.example.myapp.context.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {

    Attachment(){};
    @JsonProperty("filename")
    private String filename;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uid")
    private String uid;

}
