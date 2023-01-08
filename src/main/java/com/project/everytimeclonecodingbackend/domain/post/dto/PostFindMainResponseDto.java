package com.project.everytimeclonecodingbackend.domain.post.dto;

import com.project.everytimeclonecodingbackend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostFindMainResponseDto {
    private List<FreedomPostDto> freedomPostDtos;
    private List<SecretPostDto> secretPostDtos;
    private List<GraduatePostDto> graduatePostDtos;
    private List<FreshmanPostDto> freshmanPostDtos;
    private List<IssuePostDto> issuePostDtos;
    private List<MarketplacePostDto> marketplacePostDtos;
}
