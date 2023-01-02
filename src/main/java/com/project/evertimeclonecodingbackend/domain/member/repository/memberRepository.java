package com.project.evertimeclonecodingbackend.domain.member.repository;

import com.project.evertimeclonecodingbackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface memberRepository extends JpaRepository<Member, Long> {

}
