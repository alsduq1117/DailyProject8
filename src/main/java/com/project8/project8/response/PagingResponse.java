package com.project8.project8.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PagingResponse<T> {

    private int page;
    private int size;
    private long totalCount;
    private List<T> items;

    public PagingResponse(Page<?> page, Class<T> clazz) {
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalCount = page.getTotalElements();
        this.items = page.getContent().stream()
                .map(content -> {
                    try {
                        return clazz.getConstructor(content.getClass()).newInstance(content);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }
}
