package com.pichincha.client.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.client.domain.enums.Gender;
import com.pichincha.client.service.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  ClientDto clientDto;

  @BeforeEach
  void setup() {
    clientDto = new ClientDto();
    clientDto.setName("Christian Muyon");
    clientDto.setGender(Gender.MALE);
    clientDto.setAge(33);
    clientDto.setIdentification("1803750312");
    clientDto.setAddress("10 de agosto y Naciones Unidas");
    clientDto.setPhone("0999733321");
    clientDto.setPassword(1234);
    clientDto.setStatus(true);
  }

  @Test
  void testSaveClient() throws Exception {
    String clientJson = objectMapper.writeValueAsString(clientDto);
    ResultActions response = mockMvc.perform(post("/api/client")
        .contentType(MediaType.APPLICATION_JSON)
        .content(clientJson));
    response.andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  void testFindAllClients() throws Exception {
    mockMvc.perform(get("/api/client")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andDo(print());
  }
}
