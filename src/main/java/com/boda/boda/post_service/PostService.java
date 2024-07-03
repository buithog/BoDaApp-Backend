package com.boda.boda.post_service;

import com.boda.boda.entity.Post;
import com.boda.boda.entity.User;
import com.boda.boda.repository.PostRepository;
import com.boda.boda.repository.UserRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;

    @Value("${post.storage.directory}")
    private String postStorageDirectory;


    public List<Post> getLatestPosts(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction;

        // Kiểm tra giá trị của sortDirection và xử lý lỗi nếu không hợp lệ
        try {
            direction = Sort.Direction.valueOf(sortDirection.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Nếu sortDirection không hợp lệ, sử dụng một giá trị mặc định, ví dụ: ASC
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return postRepository.findAll(pageable).getContent();
    }

    public Post savePost(MultipartFile wordFile, MultipartFile thumnails, String userName , String author , String title ) throws IOException {
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setUploadBy(userRepository.findByEmail(userName).orElseThrow());
        Post post_save = postRepository.save(post);

        // Tạo đường dẫn cho thư mục của bài đăng
        String postDirectoryPath = postStorageDirectory + File.separator + post_save.getId();
        File postDirectory = new File(postDirectoryPath);

        // Tạo thư mục mới nếu nó không tồn tại
        if (!postDirectory.exists()) {
            postDirectory.mkdirs(); // Tạo thư mục cha và tất cả các thư mục con cần thiết
        }

        BufferedImage image = ImageIO.read(thumnails.getInputStream());

        // Tạo ByteArrayOutputStream để lưu ảnh dưới dạng byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Chuyển đổi BufferedImage thành byte array với định dạng PNG
        ImageIO.write(image, "png", byteArrayOutputStream);

        // Tạo ByteArrayInputStream từ byte array mới
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        // Lưu ảnh thumnails dưới dạng PNG
        String thumnailsFilePath = postDirectoryPath + File.separator + "thumbnails" + ".png";
        File thumnailsFile = new File(thumnailsFilePath);
        try (OutputStream outputStream = new FileOutputStream(thumnailsFile)) {
            IOUtils.copy(byteArrayInputStream, outputStream);
        }


        List<String> content =  getContentInWordFile(wordFile ,postDirectoryPath );
        List<String> imgPath =  getImgPathInWordFile(wordFile ,postDirectoryPath);
        List<Integer> positionImg =  getPositionImgInWordFile(content);
        post_save.setImagePath(imgPath);
        post_save.setContent(content);
        post_save.setPositionImg(positionImg);
        return postRepository.save(post_save);
    }

    public List<Integer> getPositionImgInWordFile(List<String> content) {
        List<Integer> l = new ArrayList<>();
        for (int i = 0 ;i<content.size();i++){
            if (content.get(i).equals("")){
                l.add(i);
            }
        }
        return l;
    }
    private List<String> getImgPathInWordFile(MultipartFile wordFile, String postDirectoryPath) throws IOException, InvalidFormatException {
        List<String> imgPathList = new ArrayList<>();
        String postDirectoryPathImg = postDirectoryPath + File.separator + "dataImg";
        File postDirectory = new File(postDirectoryPathImg);

        // Tạo thư mục mới nếu nó không tồn tại
        if (!postDirectory.exists()) {
            postDirectory.mkdirs(); // Tạo thư mục cha và tất cả các thư mục con cần thiết
        }

        try (InputStream inputStream = wordFile.getInputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFPictureData> pictures = document.getAllPictures();
            for (int i = 0; i < pictures.size(); i++) {
                XWPFPictureData pictureData = pictures.get(i);
                byte[] pictureBytes = pictureData.getData();
                String imgPath = postDirectoryPathImg + File.separator + "image" + (i + 1) + ".png";
                // Lưu hình ảnh vào thư mục của bài đăng
                try (FileOutputStream fos = new FileOutputStream(imgPath)) {
                    fos.write(pictureBytes);
                }
                imgPathList.add(imgPath);
            }
        }
        return imgPathList;
    }

    private List<String> getContentInWordFile(MultipartFile wordFile, String postDirectoryPath) throws IOException, InvalidFormatException {
        List<String> contentList = new ArrayList<>();
        try (InputStream inputStream = wordFile.getInputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                contentList.add(paragraph.getText());
            }
        }
        return contentList;
    }

}
