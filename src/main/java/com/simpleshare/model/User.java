package com.simpleshare.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	@Column(unique=true, name = "user_name")
	private String username;
	private String password;
	@OneToMany(mappedBy = "owner", cascade=CascadeType.REMOVE)
	private List<File> files;
	@OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
	private List<AccessItem> accessItems;
	
	public int getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setName(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	public File addFile(File file) {
		getFiles().add(file);
		file.setOwner(this);
		return file;
	}
	
	public File removeFile(File file) {
		getFiles().remove(file);
		file.setOwner(null);
		return file;
	}
	
	public List<AccessItem> getAccessItems() {
		return accessItems;
	}
	
	public void setAccessItems(List<AccessItem> accessItems) {
		this.accessItems = accessItems;
	}
	
	public AccessItem addAccessItem(AccessItem accessItem) {
		getAccessItems().add(accessItem);
		accessItem.setUser(this);
		return accessItem;
	}
	
	public AccessItem removeAccessItem(AccessItem accessItem) {
		getAccessItems().remove(accessItem);
		accessItem.setUser(null);
		return accessItem;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId +  ", username=" + username
				+ ", password=" + password + "]";
	}
}
