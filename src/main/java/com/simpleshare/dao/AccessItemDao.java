package com.simpleshare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
		TypedQuery<AccessItem> q = em.createQuery(
				"select a from AccessItem a", AccessItem.class);
		List<AccessItem> accessItems = q.getResultList();
		return accessItems;
	}
	
	public AccessItem getAccessItem(int accessId) {
		return em.find(AccessItem.class, accessId);
	}
	
	public List<AccessItem> getAccessItem(User user) {
		TypedQuery<AccessItem> q = em.createQuery(
				"select a from AccessItem a where a.user.userId=:userId",
				AccessItem.class);
		return q.setParameter("userId", user.getUserId()).getResultList();
	}
	
	public List<AccessItem> getAccessItem(File file) {
		TypedQuery<AccessItem> q = em.createQuery(
				"select a from AccessItem a"
				+ " where a.file.fileId=:fileId", AccessItem.class);
				return q.setParameter("fileId", file.getFileId()).getResultList();
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
