package com.example.myapp.restApiTest;

import com.example.myapp.context.request.user.Signin;
import com.example.myapp.context.request.user.Signup;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.UserModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserCrudTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserMapper userMapper;

  final String testOuathKey = "SIGNUP_TEST_OAUTH_KEY";
  final String testEmail = "whghdud17@gmail.com";

  @Test
  public void SingUpTest() throws Exception{
    final Signup signup = new Signup();
    signup.setOauthKey(testOuathKey);
    signup.setEmail(testEmail);

    mockMvc.perform(post("/user/signup")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signup)))
      .andExpect(status().isOk());
  }

  @Test
  public void SignInTest() throws Exception{
    final Signin signin = new Signin();

    signin.setOauthKey(testOuathKey);

    mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk());
  }

  @Test
  public void InfoTest() throws Exception{

    final Signin signin = new Signin();
    signin.setId(testId);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(),JwtResponse.class);

    final String accessToken = ((JwtResponse) response).getAccessToken();

    mockMvc.perform(get("/user/info")
      .header("Authorization","Bearer " + accessToken))
      .andExpect(status().isOk());

  }

  @Test
  public void WithDrawTest() throws Exception{

    final Signin signin = new Signin();
    signin.setId(testId);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(),JwtResponse.class);

    final String accessToken = ((JwtResponse) response).getAccessToken();

    mockMvc.perform(get("/user/withdraw")
      .header("Authorization","Bearer " + accessToken))
      .andExpect(status().isOk());

    userMapper.dropUserData(testId);
  }


}
