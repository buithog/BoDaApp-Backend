package com.boda.boda.controller;

import com.boda.boda.entity.Post;
import com.boda.boda.entity.Role;
import com.boda.boda.post_service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<List<Post>> getPostResponseEntity(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "0") int pageNum
    ){
        return ResponseEntity.ok(postService.getLatestPosts(pageNum,pageSize,sortBy,sortDirection));
    }

    @PostMapping
    public ResponseEntity<Post> upPost(
            @RequestParam() MultipartFile wordFile,
            @RequestParam()MultipartFile thumnails,
            @RequestParam(defaultValue = "anonimus") String author,
            @RequestParam(defaultValue = "none") String email,
            @RequestParam(defaultValue = "Boda Title") String title
            ) throws IOException {
        return  ResponseEntity.ok(postService.savePost(wordFile,thumnails,email,author,title));
    }


}
