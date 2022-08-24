package com.member.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.member.consts.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("TB_HANA_AUTHORITIES")
@Getter
public class Authority {
    
    private Long id;
    
    @Column("AUTHORITY")
    private Role role;

}