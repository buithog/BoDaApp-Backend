package com.boda.boda.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/image")
public class FileController {


    @GetMapping(value = "/imagethumbnails")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getThumnails(@RequestParam("id") String id) {
        MediaType contentType = MediaType.IMAGE_PNG;
        String baseURL = "C:\\Users\\fpt\\IdeaProjects\\BoDa\\src\\main\\resources\\";
        String imagePath =   "PostStorage" + File.separator +id+ File.separator +"thumbnails.png";
        try {
            // Create a file object
            File file = new File(baseURL+imagePath);

            // Check if the file exists and is not a directory
            if (!file.exists() || file.isDirectory()) {
                return ResponseEntity.notFound().build();
            }

            // Create an input stream from the file
            InputStream in = new FileInputStream(file);

            // Return the file as a response entity
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(new InputStreamResource(in));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getdataimage")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(@RequestParam("id") String id, @RequestParam("filename") String filename) {
        MediaType contentType = MediaType.IMAGE_PNG;

        String baseURL = "C:\\Users\\fpt\\IdeaProjects\\BoDa\\src\\main\\resources\\";
        // Construct the file path
        String path = "PostStorage" + File.separator + id + File.separator + "dataImg" + File.separator + filename;
        System.out.println(baseURL+path);

        try {
            // Create a file object
            File file = new File(baseURL+path);

            // Check if the file exists and is not a directory
            if (!file.exists() || file.isDirectory()) {
                return ResponseEntity.notFound().build();
            }

            // Create an input stream from the file
            InputStream in = new FileInputStream(file);

            // Return the file as a response entity
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(new InputStreamResource(in));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
