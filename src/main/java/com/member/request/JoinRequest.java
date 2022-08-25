package com.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  JoinRequest {
	private String userId;
    private String userName;
    private String userPassword;
    private String phoneNumber;
    private String nickName;
    private String ageRange;
}