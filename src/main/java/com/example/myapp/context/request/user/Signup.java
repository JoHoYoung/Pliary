package com.example.myapp.context.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Signup {

    @JsonProperty("email")
    private String email;

    @JsonProperty("id")
    private String id;

    public Signup(String id, String email) {
        this.id = id;
        this.email = email;
    }

}
