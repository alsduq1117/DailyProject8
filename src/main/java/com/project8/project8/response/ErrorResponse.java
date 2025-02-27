package com.project8.project8.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private String message;
    private Map<String, String> validation;


    @Builder
    public ErrorResponse(int statusCode, String message, Map<String, String> validation) {
        this.statusCode = statusCode;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }


    public void addValidaiton(String field, String message) {
        validation.put(field, message);
    }
}
