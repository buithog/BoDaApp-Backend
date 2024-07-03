package com.boda.boda.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String author;
    private String title;
    private String thumnailPath;
    private List<String> content;
    private List<String> imagePath;
    private List<Integer> positionImg;

    // Getters và setters
}
