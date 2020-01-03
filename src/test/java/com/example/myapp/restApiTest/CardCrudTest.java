package com.example.myapp.restApiTest;


import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@AutoConfigureMockMvc
public class CardCrudTest {

  final private String testId = "API_TEST_UID";
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CardMapper cardMapper;

  private String accessToken;

  @Before
  public void getAccessToken() throws Exception {
    final Signin signin = new Signin();
    signin.setId(testId);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(), JwtResponse.class);

    this.accessToken = ((JwtResponse) response).getAccessToken();
  }

  @Test
  public void createCardTest() throws Exception {
    CreateCard createCard = new CreateCard();

    createCard.setInitPeriod(5);
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
  public void readCardTest() throws Exception {
    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String cardId = ((LinkedHashMap)(response.getData().get(0))).get("id").toString();
    mockMvc.perform(get("/card/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", cardId))
      .andExpect(status().isOk());
  }

  @Test
  public void updateCardTest() throws Exception {

    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String cardId = ((LinkedHashMap)(response.getData().get(0))).get("id").toString();
    CreateCard updateCard = new CreateCard();

    updateCard.setId(cardId);
    updateCard.setInitPeriod(10);
    updateCard.setName("Update Card");
    updateCard.setNickname("Sponge bob");

    mockMvc.perform(post("/card/update")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(updateCard)))
      .andExpect(status().isOk());

    mockMvc.perform(get("/card/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", cardId))
      .andExpect(status().isOk());
  }

  @Test
  public void deleteCardTest() throws Exception {
    MvcResult result =
      mockMvc.perform(get("/card/readAll")
        .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String cardId = ((LinkedHashMap)(response.getData().get(0))).get("id").toString();

    mockMvc.perform(get("/card/delete")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", cardId))
      .andExpect(status().isOk());

//    cardMapper.dropCard(testId);
  }

}
