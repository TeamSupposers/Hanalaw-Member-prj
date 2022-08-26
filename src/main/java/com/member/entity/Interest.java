package com.member.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.member.consts.Concern;

import lombok.Getter;
import lombok.ToString;

@ToString
@Table("TB_HANA_INTEREST")
@Getter
public class Interest {
    
	@Id
	@Column("INTEREST_ID")
    private Long id;
    
	@Column("MBR_ID")
    private Long memberId;
	
    @Column("INTEREST")
    private Concern concern;

    public Interest(Long memberId, Concern concern){
    	this.memberId = memberId;
    	this.concern = concern;
    }
}