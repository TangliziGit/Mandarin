package org.a3.mandarin.common.entity;

import javax.persistence.*;

@Entity
@Table(name = "setting")
public class Setting {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer settingId;
	
	@Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double value;
	
    public Setting() {}
    
    public Setting(String name, Double value) {
    	this.name = name;
    	this.value = value;
    }
    
    public Integer getSettingId() {
    	return this.settingId;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public Double getValue() {
    	return this.value;
    }
    
    public void setValue(Double value) {
    	this.value = value;
    }
	
}