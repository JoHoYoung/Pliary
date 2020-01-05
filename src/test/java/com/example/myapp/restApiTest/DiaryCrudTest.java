package com.example.myapp.restApiTest;

import com.example.myapp.context.request.user.Signin;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
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
public class DiaryCrudTest {

  final private String testOauthKey = "API_TEST_OAUTH_KEY";
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  static CardMapper cardMapper;
  static int testUserId = 10;

  @Autowired
  UserMapper userMapper;

  private String accessToken;

  @BeforeClass
  public static void makeCard(){
    cardMapper.createCard(testUserId,1,"ForDiary","ForDiary",
      "ForDiary","ForDiary",10,10);
  }

  @Before
  public void getAccessToken() throws Exception {
    final Signin signin = new Signin();
    signin.setOauthKey(testOauthKey);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(), JwtResponse.class);

    this.accessToken = ((JwtResponse) response).getAccessToken();

  }

}
