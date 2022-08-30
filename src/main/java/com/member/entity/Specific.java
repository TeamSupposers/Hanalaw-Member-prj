package com.member.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.ToString;

@ToString
@Table("TB_HANA_MBR_SPEC")
@Getter
public class Specific {
    
	@Id
	@Column("SPEC_ID")
    private Long id;
    
	@Column("MBR_ID")
    private Long memberId;
	
    @Column("REFRESH_TOKEN")
    private String refreshToken;

    @Column("CREATE_DT")
    private LocalDateTime createDt = LocalDateTime.now();
    
    public Specific(Long memberId, String refreshToken) {
    	this.memberId = memberId;
    	this.refreshToken = refreshToken;
    }
}