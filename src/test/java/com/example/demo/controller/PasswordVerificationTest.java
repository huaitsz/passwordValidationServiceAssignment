package com.example.demo.controller;

import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;

import static org.hamcrest.Matchers.hasSize;

class PasswordVerificationTest extends AbstractRestControllerTest {
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
