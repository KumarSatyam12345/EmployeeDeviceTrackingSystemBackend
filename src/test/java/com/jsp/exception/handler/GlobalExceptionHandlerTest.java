package com.jsp.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.exception.DummyController;
import com.jsp.exception.DummyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DummyController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnValidationError() throws Exception {

        DummyRequest req = new DummyRequest();
        mockMvc.perform(post("/device/saveDevice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.errors.dModel").value("dModel cannot be blank"))
                .andExpect(jsonPath("$.path").value("/device/saveDevice"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
