package com.project.everytimeclonecodingbackend.domain.post.dto;

public class PostSaveResponseDto {

    private long postId;

    private PostSaveResponseDto() {
    }

    public PostSaveResponseDto(long postId) {
        this.postId = postId;
    }

    public long getPostId() {
        return postId;
    }
}
