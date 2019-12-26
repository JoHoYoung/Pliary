package com.example.myapp.context.request.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {

  @JsonProperty("filename")
  private String filename;

  @JsonProperty("type")
  private String type;

  @JsonProperty("id")
  private String id;

}
