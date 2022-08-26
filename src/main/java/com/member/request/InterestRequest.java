package com.member.request;

import java.util.List;

import com.member.consts.Concern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  InterestRequest {
	private Long memberId;
	private List<Concern> interest;
}