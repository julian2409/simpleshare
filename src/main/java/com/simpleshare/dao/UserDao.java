package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.simpleshare.model.File;
import com.simpleshare.model.User;

public class UserDao {

	private static EntityManager em;
	private static UserDao singleton;
	
	private UserDao() {
		em = DaoManager.getInstance().getEntityManager();
	}
	
	public static UserDao getInstance() {
		if (singleton == null) {
			singleton = new UserDao();
		}
		return singleton;
	}
	
	public List<User> getUsers() {
		Query q = em.createQuery("select u from User u");
		List<User> users = q.getResultList();
		return users;
	}
	
	public User findUser(int userId) {
		return em.find(User.class, userId);
	}
	
	public User findUserByName(String name) {
		return (User) em.createQuery("select u from User u"
				+ " where u.username=:username")
				.setParameter("username", name).getSingleResult();
	}
	
	public User saveUser(User user) {
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user;
	}
	
	public User deleteUser(User user) {
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
		return user;
	}
	
	public void updateUser(User user) {
		User userInDb = em.find(User.class, user.getUserId());
		userInDb.setName(user.getUserName());
		userInDb.setPassword(user.getPassword());
	}
	
	public File createFile(User user, File file) {
		User userInDb = em.find(User.class, user.getUserId());
		file = userInDb.addFile(file);
		return file;
	}
	
	public List<File> getFiles(User user) {
		return em.find(User.class, user.getUserId()).getFiles();
	}
	
	public File updateFile(User user, File file) {
		User userInDb = em.find(User.class, user.getUserId());
		List<File> userFiles = userInDb.getFiles();
		for (File f : userFiles) {
			if (f.getFileId() == file.getFileId()) {
				f.setName(file.getName());
				f.setPath(file.getPath());
				f.setOwner(file.getOwner());
				return f;
			}
		}
		return null;
	}
	
	public File deleteFile(User user, File file) {
		User userInDb = em.find(User.class, user.getUserId());
		File deletedFile = userInDb.removeFile(file);
		return deletedFile;
	}
}
