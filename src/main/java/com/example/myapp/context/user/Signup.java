package com.example.myapp.context.user;

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

    @JsonProperty("uid")
    private String uid;

    public Signup(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

}
