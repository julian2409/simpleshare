package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.simpleshare.model.File;
import com.simpleshare.model.User;

public class FileDao {

	private static EntityManager em;
	private static FileDao singleton;
	
	private FileDao() {
		em = DaoManager.getInstance().getEntityManager();
	}
	
	public static FileDao getInstance() {
		if (singleton == null) {
			singleton = new FileDao();
		}
		return singleton;
	}
	
	public File saveFile(File file) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(file);
		em.getTransaction().commit();
		return file;
	};
	
	public List<File> getFiles() {
		TypedQuery<File> q = em.createQuery("select f from File f", File.class);
		return q.getResultList();
	}
	
	public File getFile(Integer fileId) {
		return em.find(File.class, fileId);
	}
	
	public List<File> getFiles(User owner) {
		TypedQuery<File> q = em.createQuery(
				"select f from File f where f.owner.userId=:owner",
				File.class);
		q.setParameter("owner", owner.getUserId());
		List<File> files = q.getResultList();
		return files;
	}
	
	public void updateFile(File file) {
		File fileInDb = em.find(File.class, file.getFileId());
		fileInDb.setPath(file.getPath());
		fileInDb.setName(file.getName());
	}
	
	public File deleteFile(File file) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.remove(file);
		em.getTransaction().commit();
		return file;
	}
	
}
