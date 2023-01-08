package com.project.everytimeclonecodingbackend.domain.like.repository;

import com.project.everytimeclonecodingbackend.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long > {
}
