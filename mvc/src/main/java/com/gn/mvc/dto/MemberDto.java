package com.gn.mvc.dto;

import com.gn.mvc.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDto {
	private Long member_no;
	private String member_id;
	private String member_pw;
	private String member_name;
	
	public Member toEntity() {
		return Member.builder()
				.memberNo(member_no)
				.memberId(member_id)
				.memberPw(member_pw)
				.memberName(member_name)
				.build();
	}
	
	public MemberDto toDto(Member member) {
		return MemberDto.builder()
				.member_no(member.getMemberNo())
				.member_id(member.getMemberId())
				.member_pw(member.getMemberPw())
				.member_name(member.getMemberName())
				.build();
	}
}
