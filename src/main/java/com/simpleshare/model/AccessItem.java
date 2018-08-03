package com.simpleshare.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "access_item", uniqueConstraints={
		@UniqueConstraint(columnNames = {"user", "file"})
	})
public class AccessItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_id")
	private int accessId;
	private String permissions;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="user")
	private User user;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="file")
	private File file;
	
	public int getAccessId() {
		return accessId;
	}
	
	public String getPermissions() {
		return permissions;
	}
	
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return "AccessItem [accessId=" + accessId + ", permissions=" + permissions
				+ ", user=" + user + ", file=" + file + "]";
	}
}
