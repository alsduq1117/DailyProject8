package com.project8.project8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project8.project8.controller.PostController;
import com.project8.project8.request.PostCreate;
import com.project8.project8.response.PostResponse;
import com.project8.project8.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("글 작성")
    void test1() throws Exception {
        PostCreate request = new PostCreate("제목", "내용");
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(request);

        PostResponse response = new PostResponse(1L, "제목", "내용");

        Mockito.when(postService.write(any(PostCreate.class))).thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 id"),
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                        )
                ));
    }

    @Test
    @DisplayName("글 단건조회")
    void test2() throws Exception {
        PostResponse response = new PostResponse(1L, "제목", "내용");

        Mockito.when(postService.get(anyLong())).thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("post-inquiry",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 id")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 id"),
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                        )
                ));
    }
}
