package com.simpleshare.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "file_data", uniqueConstraints={
	@UniqueConstraint(columnNames = {"path", "name"})
})
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private int fileId;
	private String path;
	private String name;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="owner")
	private User owner;
	@OneToMany(mappedBy = "file", cascade=CascadeType.REMOVE)
	private List<AccessItem> accessItems;
	
	public int getFileId() {
		return fileId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
