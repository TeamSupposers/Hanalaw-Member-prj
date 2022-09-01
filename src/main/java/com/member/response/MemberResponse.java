package com.member.response;

import java.util.List;

import com.member.consts.Concern;
import com.member.entity.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class  MemberResponse {
	private String userId;
    private String userName;
    private String phoneNumber;
    private String nickName;
    private String ageRange;
    private List<Concern> interest;
    
    public MemberResponse(Member member) {
    	this.userId = member.getUserId();
    	this.userName = member.getUsername();
    	this.phoneNumber = member.getPhoneNumber();
    	this.nickName = member.getNickName();
    	this.ageRange = member.getAgeRange();
    	this.interest = member.getInterests();
    }
}