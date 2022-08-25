package com.member.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.member.consts.Interest;
import com.member.consts.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table("TB_HANA_MBR")
public class Member implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("MBR_ID")
    private Long memberId;
    
    @Column("USER_ID")
    private String userId;
    
    @Column("USER_NAME")
    private String userName;

    @Column("USER_PASSWORD")
    private String userPassword;

    @Column("PHONENUMBER")
    private String phoneNumber;
    
    @Column("NICKNAME")
    private String nickName;
    
    @Column("AGE_RANGE")
    private String ageRange;
    
    @Setter
    @Transient
    @JsonIgnore
    private List<Interest> interests;
    
    @Setter
    @Column("ENABLED")
    private Boolean enabled;
    
    public Member(String userId, String userName, String userPassword) {
    	this.userId = userId;
    	this.userName = userName;
    	this.userPassword = userPassword;
    	this.enabled = true;
    }
    
    @Setter
    @Transient
    @JsonIgnore
    private List<Role> roles;

    @Override
    public String getUsername() {
        return userId;
    }

    public void setUsername(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPassword;
    }

    @JsonProperty
    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}