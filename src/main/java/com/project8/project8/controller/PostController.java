package com.project8.project8.controller;

import com.project8.project8.request.PostCreate;
import com.project8.project8.request.PostEdit;
import com.project8.project8.request.PostSearch;
import com.project8.project8.response.PagingResponse;
import com.project8.project8.response.PostResponse;
import com.project8.project8.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public PostResponse write(@RequestBody @Valid PostCreate postCreate){
        return postService.write(postCreate);
    }

    @GetMapping("/{postId}")
    public PostResponse get(@PathVariable(name = "postId")Long postId){
        return postService.get(postId);
    }

    @GetMapping("")
    public PagingResponse<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PutMapping("/{postId}")
    public PostResponse edit(@PathVariable(name = "postId")Long postId, @RequestBody @Valid PostEdit postEdit){
        return postService.edit(postId, postEdit);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> edit(@PathVariable(name = "postId")Long postId){
        postService.delete(postId);

        return ResponseEntity.noContent().build();
    }
}
