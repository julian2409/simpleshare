package com.simpleshare.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoManager {

	private static final String PERSISTENCE_UNIT_NAME = "simpleshare";
	private static EntityManagerFactory factory;
	
	private static DaoManager dm;
	private static EntityManager em;
	
	private DaoManager() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	public static DaoManager getInstance() {
		if (dm == null) {
			dm = new DaoManager();
		}
		return dm;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
}
