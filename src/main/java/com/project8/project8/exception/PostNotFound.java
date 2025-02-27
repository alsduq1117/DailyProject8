package com.project8.project8.exception;

import org.springframework.http.HttpStatus;

public class PostNotFound extends CustomException{

    private static final String MESSAGE = "해당하는 게시글을 찾을 수 없습니다.";


    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
