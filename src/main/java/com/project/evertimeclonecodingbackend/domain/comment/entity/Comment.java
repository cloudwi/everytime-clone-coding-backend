package com.project.evertimeclonecodingbackend.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Comment {

    @Id
    private long id;
    @Column
    private String content;
}
