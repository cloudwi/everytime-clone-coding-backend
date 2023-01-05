package com.project.evertimeclonecodingbackend.domain.member.repository;

import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String getuserId);
}
