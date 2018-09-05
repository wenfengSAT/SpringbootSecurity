package com.security.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resources")
public class ResourceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "r_id")
	private Long id;

	@Column(name = "r_name")
	private String name;

	@Column(name = "p_id")
	private String pId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

}
