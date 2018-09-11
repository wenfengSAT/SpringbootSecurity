package com.security.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

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
	private Long pId;
	
	@Transient
	private List<ResourceEntity> children;

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

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public List<ResourceEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceEntity> children) {
		this.children = children;
	}

}
