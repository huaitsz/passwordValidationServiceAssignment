package com.example.demo.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;

import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PasswordVerificationTest {
  @Autowired
  private MockMvc mockMvc;

  public MockMvc getMockMvc() {
     return mockMvc;
  }

  @Test
  void controllerValidate() throws Exception {
    final MockHttpServletRequestBuilder mockRequest = post("/password/verification")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .accept(MediaType.APPLICATION_JSON)
      .content("password=!!!!!!!!!!!!!!");

    final MvcResult result = getMockMvc()
      .perform(mockRequest)
      .andExpect(request().asyncStarted())
      .andReturn();

    getMockMvc().perform(asyncDispatch(result))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.messages",  hasSize(4)));
  }
}
