package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.simpleshare.model.File;

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
	
	public List<File> getAllFiles() {
		Query q = em.createQuery("select f from File f");
		List<File> files = q.getResultList();
		return files;
	}
	
	public File getFile(Integer fileId) {
		return em.find(File.class, fileId);
	}
	
	public List<File> getFilesForUser(int userId) {
		Query q = em.createQuery("select f from File f where f.owner.userId=:owner");
		q.setParameter("owner", userId);
		List<File> files = q.getResultList();
		return files;
	}
	
	
}
