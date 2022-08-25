package com.member.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.member.consts.Role;

import lombok.Getter;
import lombok.ToString;

@ToString
@Table("TB_HANA_AUTHORITIES")
@Getter
public class Authority {
    
	@Id
	@Column("AUTH_ID")
    private Long id;
    
	@Column("MBR_ID")
    private Long memberId;
	
    @Column("AUTHORITY")
    private Role role;

    public Authority(Long memberId, Role role){
    	this.memberId = memberId;
    	this.role = role;
    }
}