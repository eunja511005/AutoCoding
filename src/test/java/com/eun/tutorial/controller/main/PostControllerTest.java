package com.eun.tutorial.controller.main;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.service.main.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    void testSavePosts() throws Exception {
        // Given
        PostDTO postDTO = new PostDTO();
        postDTO.setId("");
        postDTO.setVisibility("1");
        postDTO.setOpenDate(null);
        postDTO.setPostType("Free");
        postDTO.setTitle("Test Title");
        postDTO.setContent("<p>1</p>");

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Post saved successfully");

        when(postService.save(any(PostDTO.class))).thenReturn(response);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/posts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                //.header("x-xsrf-token", "49a00ac6-a19d-49ca-940d-bb678dde35b2")
                .header("sec-fetch-mode", "cors")
                .header("content-length", "106")
                .header("referer", "http://localhost:8080/")
                .header("sec-fetch-site", "same-origin")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("cookie", "XSRF-TOKEN=49a00ac6-a19d-49ca-940d-bb678dde35b2; JSESSIONID=4C6528D93A8C32E87509FE63880B1893")
                .header("origin", "http://localhost:8080")
                .header("pragma", "no-cache")
                .header("accept", "*/*")
                .header("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("host", "localhost:8080")
                .header("x-requested-with", "XMLHttpRequest")
                .header("connection", "keep-alive")
                .header("content-type", "application/json; charset=UTF-8")
                .header("cache-control", "no-cache")
                .header("accept-encoding", "gzip, deflate, br")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .header("sec-fetch-dest", "empty")
                .content(new ObjectMapper().writeValueAsString(postDTO));

        // Then
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Post saved successfully"))
                .andDo(MockMvcResultHandlers.print());

        verify(postService, times(1)).save(any(PostDTO.class));
    }

}
