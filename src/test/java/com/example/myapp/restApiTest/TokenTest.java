package com.example.myapp.restApiTest;

import com.example.myapp.context.request.token.Refresh;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TokenTest {
  final String testId = "API_TEST_UID";

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void TokenRefreshTest() throws Exception{

    final Signin signin = new Signin();
    signin.setId(testId);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(),JwtResponse.class);

    final String accessToken = ((JwtResponse) response).getAccessToken();
    final String refreshToken = ((JwtResponse) response).getRefreshToken();

    final Refresh refresh = new Refresh(accessToken, refreshToken);

    mockMvc.perform(post("/token/refresh")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(refresh)))
      .andExpect(status().isOk());
  }

}
