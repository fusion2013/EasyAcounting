package com.fusion.ea.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="file_id")
	private File file;
	
	@Column(name="deleted")
	private boolean deleted;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="modified_at")
	private Date modifiedAt;
	@Column(name="deleted_at")
	private Date deletedAt;
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name="modified_by")
	private User modifiedBy;
	@ManyToOne
	@JoinColumn(name="deleted_by")
	private User deletedBy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public User getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public User getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}
	
}
