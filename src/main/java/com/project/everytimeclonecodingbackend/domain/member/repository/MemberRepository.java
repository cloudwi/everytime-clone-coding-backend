package com.project.everytimeclonecodingbackend.domain.member.repository;

import com.project.everytimeclonecodingbackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
}
