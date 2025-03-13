package com.gn.mvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name="member")
@Getter
@Builder
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_no")
	private Long memberNo;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="member_pw")
	private String memberPw;
	
	@Column(name="member_name")
	private String memberName;
}
