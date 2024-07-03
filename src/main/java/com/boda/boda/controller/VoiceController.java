package com.boda.boda.controller;


import com.boda.boda.entity.Voice;
import com.boda.boda.post_service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/voice")
@RequiredArgsConstructor
public class VoiceController {
    @Autowired
    VoiceService voiceService;

    @GetMapping("/getvoice")
    @ResponseBody
    public ResponseEntity<List<Voice>> getListVoice(
        @RequestParam(defaultValue = "DESC") String sortDirection,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "5") int pageSize,
        @RequestParam(defaultValue = "0") int pageNum
    ){
        return ResponseEntity.ok(voiceService.getVoice(sortBy,sortDirection,pageNum,pageSize));
    }
    @GetMapping("/getvoice/{id}")
    @ResponseBody
    public ResponseEntity<Voice> getVoiceById(@PathVariable String id){
        return ResponseEntity.ok(voiceService.getVoiceById(id));
    }

    @PostMapping("/savevoice")
    @ResponseBody
    public ResponseEntity<Voice> saveVoice(@RequestParam("fileMp3") MultipartFile fileMp3 ,
                                                 @RequestParam("Thumnails") MultipartFile thumnails,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("name") String name
                                                 ) throws IOException {
        return ResponseEntity.ok(voiceService.saveVoice(fileMp3,thumnails,description,name));
    }

    @GetMapping(value = "/voicethumnails/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getThumnails(@PathVariable String id) throws IOException {
        MediaType contentType = MediaType.IMAGE_PNG;
        String imagePath = "C:\\Users\\fpt\\IdeaProjects\\BoDa\\src\\main\\resources\\" + "VoiceStorage" + File.separator + id + File.separator + "thumnails.png";
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            throw new FileNotFoundException("Thumbnail not found with id " + id);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }

    @CrossOrigin
    @GetMapping(value = "/voicefile/{id}")
    @Async
    @ResponseBody
    public ResponseEntity<InputStreamResource> getVoiceFile(@PathVariable String id) throws IOException {
        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        String mp3FilePath = "C:\\Users\\fpt\\IdeaProjects\\BoDa\\src\\main\\resources\\" + "VoiceStorage" + File.separator + id + File.separator + "voice.mp3";
        File mp3File = new File(mp3FilePath);

        if (!mp3File.exists()) {
            throw new FileNotFoundException("MP3 file not found with id " + id);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(mp3File));
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }

}
