package com.member.request;

import java.util.List;

import com.member.consts.Concern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  UpdateRequest {
	private String uuid;
	private Long memberId;
    private String userPassword;
    private String phoneNumber;
    private String nickName;
    private String ageRange;
	private List<Concern> interest;
}