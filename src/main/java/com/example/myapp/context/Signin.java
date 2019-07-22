package com.example.myapp.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Signin {

    @JsonProperty("email")
    private String email;

    public Signin(String email, String password) {
        this.email = email;
    }
}