package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "setting")
public class Setting {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer settingId;
	
	@Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer number;
	
    public Setting() {}
    
    public Setting(Integer settingId, String name, Integer number) {
    	this.settingId = settingId;
    	this.name = name;
    	this.number = number;
    }
    
    public Integer getSettingId() {
    	return this.settingId;
    }
    
    public void setSettingId(Integer settingId) {
    	this.settingId = settingId;
    } 
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public Integer getNumber() {
    	return this.number;
    }
    
    public void setNumber(Integer number) {
    	this.number = number;
    }
	
}