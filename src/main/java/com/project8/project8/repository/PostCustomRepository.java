package com.project8.project8.repository;

import com.project8.project8.domain.Post;
import com.project8.project8.request.PostSearch;
import org.springframework.data.domain.Page;

public interface PostCustomRepository {

    Page<Post> getList(PostSearch postSearch);
}
