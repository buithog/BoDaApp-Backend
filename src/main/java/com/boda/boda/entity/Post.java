package com.boda.boda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "post")
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User uploadBy;

    private String author;

    private String title;

    private String thumnailPath;

    @Column(columnDefinition = "TEXT")
    @ElementCollection
    private List<String> content;

    @ElementCollection
    private List<String> imagePath;

    @ElementCollection
    private List<Integer> positionImg;


}
