package com.example.myapp.context.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResult {
    String accessToken;
    String refreshToken;
}
