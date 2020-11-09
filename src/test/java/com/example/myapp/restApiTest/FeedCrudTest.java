package com.example.myapp.restApiTest;


import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.context.request.feed.UpdateFeed;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.Card;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeedCrudTest {
  final private String testOauthKey = "API_TEST_OAUTH_KEY";

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserMapper userMapper;
  @Autowired
  CardMapper cardMapper;
  @Autowired
  FeedMapper feedMapper;

  private String accessToken;
  private int testUserId;

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
    this.testUserId = userMapper.getUserId(this.testOauthKey);
  }

  @Test
  public void AcreateFeedTest() throws Exception {
    CreateCard createCard = new CreateCard();

    createCard.setWaterPeriod(5);
    createCard.setName("Test Card For Feed");
    createCard.setNickname("Test Card For Feed");

    mockMvc.perform(post("/card/create")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(createCard)))
      .andExpect(status().isOk());

    List<Card> cards = cardMapper.readAllCard(this.testUserId);
    int cardId = cards.get(0).getId();

    CreateFeed createFeed = new CreateFeed();
    createFeed.setCardId(cardId);
    createFeed.setOverDegree(3);
    createFeed.setFeedAt(new Date());

    mockMvc.perform(post("/feed/create")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(createFeed)))
      .andExpect(status().isOk());

  }

  @Test
  public void BreadFeedTest() throws Exception {
    List<Card> cards = cardMapper.readAllCard(this.testUserId);
    int cardId = cards.get(0).getId();

    MvcResult result =
      mockMvc.perform(get("/feed/readAll")
        .header("Authorization", "Bearer " + accessToken)
        .param("id", Integer.toString(cardId)))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String feedId = ((LinkedHashMap) (response.getData().get(0))).get("id").toString();

    mockMvc.perform(get("/feed/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", feedId))
      .andExpect(status().isOk());

  }

  @Test
  public void CupdateFeedTest() throws Exception {
    List<Card> cards = cardMapper.readAllCard(this.testUserId);
    int cardId = cards.get(0).getId();

    MvcResult result =
      mockMvc.perform(get("/feed/readAll")
        .header("Authorization", "Bearer " + accessToken)
        .param("id", Integer.toString(cardId)))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    int feedId = (int) ((LinkedHashMap) (response.getData().get(0))).get("id");

    UpdateFeed updateFeed = new UpdateFeed();
    updateFeed.setFeedAt(new Date());
    updateFeed.setId(feedId);
    updateFeed.setOverDegree(100);

    mockMvc.perform(post("/feed/update")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(updateFeed)))
      .andExpect(status().isOk());
  }

  @Test
  public void DdeleteFeedTest() throws Exception {
    List<Card> cards = cardMapper.readAllCard(this.testUserId);
    int cardId = cards.get(0).getId();

    MvcResult result =
      mockMvc.perform(get("/feed/readAll")
        .header("Authorization", "Bearer " + accessToken)
        .param("id", Integer.toString(cardId)))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    int feedId = (int) ((LinkedHashMap) (response.getData().get(0))).get("id");
    mockMvc.perform(get("/feed/delete")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(feedId)))
      .andExpect(status().isOk());

    feedMapper.dropFeed(feedId);
  }


}
