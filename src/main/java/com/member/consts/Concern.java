package com.member.consts;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Concern {
	
	TRAFFIC("����"),
	HEALTH("����"),
	LIVING("��Ȱ"),
	DISORDER("���"),
	TRAVLE("����"),
	BUSINESS("���"),
	WORK("�뵿"),
	TECHNOLOGY("���"),
	EDUCATION("����"),
	MILLITARY("����"),
	PROPERTY("�ε���"),
	TELECAST("������");
	
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
