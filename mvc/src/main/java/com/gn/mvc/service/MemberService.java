package com.gn.mvc.service;

import org.springframework.stereotype.Service;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.entity.Member;
import com.gn.mvc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	
	public MemberDto createMember(MemberDto dto) {
		
		Member param = dto.toEntity();	
		Member result = repository.save(param);
		
		return new MemberDto().toDto(result);
	}

}
