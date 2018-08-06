package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.simpleshare.dao.DaoManager;
import com.simpleshare.model.AccessItem;
import com.simpleshare.model.File;
import com.simpleshare.model.User;

public class AccessItemDao {
	
	private static EntityManager em;
	private static AccessItemDao singleton;
	
	private AccessItemDao() {
		em = DaoManager.getInstance().getEntityManager();
	}
	
	public static AccessItemDao getInstance() {
		if (singleton == null) {
			singleton = new AccessItemDao();
		}
		return singleton;
	}
	
	public List<AccessItem> getAccessItems() {
		Query q = em.createQuery("select a from AccessItem a");
		List<AccessItem> accessItems = q.getResultList();
		return accessItems;
	}
	
	public AccessItem getAccessItem(int accessId) {
		return em.find(AccessItem.class, accessId);
	}
	
	public List<AccessItem> getAccessItem(User user) {
		return (List<AccessItem>) em.createQuery("select a from accessitem a"
				+ " where a.user.userId=:userId")
				.setParameter("userId", user.getUserId());
	}
	
	public List<AccessItem> getAccessItem(File file) {
		return (List<AccessItem>) em.createQuery("select a from accessitem a"
				+ " where a.file.fileId=:fileId")
				.setParameter("userId", file.getFileId());
	}
	
	public AccessItem saveAccessItem(AccessItem accessItem) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.persist(accessItem);
		em.getTransaction().commit();
		return accessItem;
	}
	
	public void updateAccessItem(AccessItem accessItem) {
		AccessItem itemInDb = em.find(AccessItem.class, accessItem.getAccessId());
		itemInDb.setPermissions(accessItem.getPermissions());
	}
	
	public AccessItem deleteAccessItem(AccessItem accessItem) {
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		em.remove(accessItem);
		em.getTransaction().commit();
		return accessItem;
	}
}
