package com.boda.boda.post_service;

import com.boda.boda.entity.Post;
import com.boda.boda.entity.Voice;
import com.boda.boda.repository.PostRepository;
import com.boda.boda.repository.UserRepository;
import com.boda.boda.repository.VoiceRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoiceService {

    private final VoiceRepository repository;

    @Value("${voice.storage.directory}")
    private String voiceStorageDirectory;

    public Voice getVoiceById(String id){
        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    public List<Voice> getVoice(){
        return repository.findAll();
    }

    public Voice saveVoice(MultipartFile fileMp3, MultipartFile thumnails,  String description ,String name) throws IOException {
        Voice v = new Voice();
        v.setDescription(description);
        v.setName(name);
        Voice x = repository.save(v);

        // Tạo đường dẫn cho thư mục của bài đăng
        String voiceDirectoryPath = voiceStorageDirectory + File.separator + x.getId();
        File postDirectory = new File(voiceDirectoryPath);

        // Tạo thư mục mới nếu nó không tồn tại
        if (!postDirectory.exists()) {
            postDirectory.mkdirs(); // Tạo thư mục cha và tất cả các thư mục con cần thiết
        }

        // Lưu ảnh thumnails dưới dạng PNG
        BufferedImage image = ImageIO.read(thumnails.getInputStream());
        String thumnailsFilePath = voiceDirectoryPath + File.separator + "thumnails" + ".png";
        File thumnailsFile = new File(thumnailsFilePath);
        try (OutputStream outputStream = new FileOutputStream(thumnailsFile)) {
            ImageIO.write(image, "png", outputStream);
        }

        // Lưu file MP3
        String mp3FilePath = voiceDirectoryPath + File.separator + "voice.mp3";
        File mp3File = new File(mp3FilePath);
        try (InputStream mp3InputStream = fileMp3.getInputStream();
             OutputStream mp3OutputStream = new FileOutputStream(mp3File)) {
            IOUtils.copy(mp3InputStream, mp3OutputStream);
        }

        x.setLocation(mp3FilePath);
        x.setLocationThumnails(thumnailsFilePath);
        return repository.save(x);

    }

    public List<Voice> getVoice(String sortBy, String sortDirection, int pageNum, int pageSize) {

        Sort.Direction  direction = Sort.Direction.valueOf(sortDirection);
        Pageable pageable = PageRequest.of(pageNum,pageSize,Sort.by(direction,sortBy));
        return repository.findAll(pageable).getContent();
    }
}
