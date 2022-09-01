package com.member.consts;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Concern {
	
	TRAFFIC("교통"),
	HEALTH("보건"),
	LIVING("생활"),
	DISORDER("장애"),
	TRAVLE("여행"),
	BUSINESS("사업"),
	WORK("노동"),
	TECHNOLOGY("기술"),
	EDUCATION("교육"),
	MILLITARY("국방"),
	PROPERTY("부동산"),
	TELECAST("방송통신");
	
	private final String value;

	public List<Concern> getList() {
		List<Concern> list = new ArrayList<>();
		list.add(Concern.TRAFFIC);
		list.add(Concern.HEALTH);
		list.add(Concern.LIVING);
		list.add(Concern.DISORDER);
		list.add(Concern.TRAVLE);
		list.add(Concern.BUSINESS);
		list.add(Concern.TECHNOLOGY);
		list.add(Concern.EDUCATION);
		list.add(Concern.MILLITARY);
		list.add(Concern.PROPERTY);
		list.add(Concern.TELECAST);
		return list;
	}
}
