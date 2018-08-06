package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.simpleshare.model.AccessItem;
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
		TypedQuery<User> q = em.createQuery(
				"select u from User u", User.class);
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
	
	public File addFile(User user, File file) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		User userinDb = em.find(User.class, user.getUserId());
		userinDb.addFile(file);
		updateUser(userinDb);
		em.persist(file);
		em.getTransaction().commit();
		return file;
	}
	
	public File removeFile(File file) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.remove(file);
		em.getTransaction().commit();
		return file;
	}
	
	public AccessItem addAccessItem(User user, File file, AccessItem accessItem) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		User userInDb = em.find(User.class, user.getUserId());
		File fileInDb = em.find(File.class, file.getFileId());
		userInDb.addAccessItem(accessItem);
		fileInDb.addAccessItem(accessItem);
		em.refresh(userInDb);
		em.refresh(fileInDb);
		accessItem.setUser(userInDb);
		accessItem.setFile(fileInDb);
		em.persist(accessItem);
		em.getTransaction().commit();
		return accessItem;
	}
	
	public AccessItem removeAccessItem(AccessItem accessItem) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.remove(accessItem);
		em.getTransaction().commit();
		return accessItem;
	}
}
