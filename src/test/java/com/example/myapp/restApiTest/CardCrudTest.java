package com.example.myapp.restApiTest;


import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.card.UpdateCard;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
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


import java.util.LinkedHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class CardCrudTest {

  final private String testOauthKey = "API_TEST_OAUTH_KEY";
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  CardMapper cardMapper;
  @Autowired
  UserMapper userMapper;

  private String accessToken;
  private int userId;

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

    // Clear Data
    this.userId = userMapper.getUserId(testOauthKey);
  }

  @Test
  public void AcreateCardTest() throws Exception {
    cardMapper.dropCard(userId);

    CreateCard createCard = new CreateCard();

    createCard.setWaterPeriod(5);
    createCard.setName("TEST Plant");
    createCard.setNickname("Sponge bob");

    // 카드 갯수제한 까지
    for (int i = 0; i < 6; i++) {
      mockMvc.perform(post("/card/create")
        .header("Authorization", "Bearer " + accessToken)
        .contentType(APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsString(createCard)))
        .andExpect(status().isOk());
    }

    //갯수 제한을 넘은경우 테스트
    mockMvc.perform(post("/card/create")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(createCard)))
      .andExpect(status().isNotAcceptable());

  }


  @Test
  public void BreadCardTest() throws Exception {
    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String cardId = ((LinkedHashMap) (response.getData().get(0))).get("id").toString();
    mockMvc.perform(get("/card/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", cardId))
      .andExpect(status().isOk());
  }

  @Test
  public void CupdateCardTest() throws Exception {

    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    int cardId = (int) ((LinkedHashMap) (response.getData().get(0))).get("id");
    UpdateCard updateCard = new UpdateCard();

    updateCard.setId(cardId);
    updateCard.setTypeId(3);
    updateCard.setWaterPeriod(10);
    updateCard.setEngName("Kind of Plant");
    updateCard.setKrName("임의의 식물");
    updateCard.setName("Update Card");
    updateCard.setNickname("Sponge bob");

    mockMvc.perform(post("/card/update")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(updateCard)))
      .andExpect(status().isOk());

    mockMvc.perform(get("/card/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(cardId)))
      .andExpect(status().isOk());
  }

  @Test
  public void DeleteCardTest() throws Exception {
    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String cardId = ((LinkedHashMap) (response.getData().get(0))).get("id").toString();

    System.out.println(cardId);
    mockMvc.perform(get("/card/delete")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", cardId))
      .andExpect(status().isOk());

    cardMapper.dropCard(userId);
  }

}
