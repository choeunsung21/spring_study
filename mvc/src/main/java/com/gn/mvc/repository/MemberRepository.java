package com.gn.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.Member;
import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByMemberId(String keyword);
}
