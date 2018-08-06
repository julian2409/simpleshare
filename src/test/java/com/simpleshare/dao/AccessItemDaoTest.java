package com.simpleshare.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.simpleshare.PrepareTests;
import com.simpleshare.model.AccessItem;
import com.simpleshare.model.File;
import com.simpleshare.model.User;

public class AccessItemDaoTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PrepareTests.initDatabase();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSaveAccessitem() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		User user = new User();
		user.setName("testUser03");
		user.setPassword("secret");
		uDao.saveUser(user);
		File file = new File();
		file.setName("test03.txt");
		file.setPath("/home/julian/");
		file.setOwner(user);
		fDao.saveFile(file);
		AccessItem accessItem = new AccessItem();
		accessItem.setPermissions("r--");
		accessItem.setUser(user);
		accessItem.setFile(file);
		dao.saveAccessItem(accessItem);
		assertTrue(accessItem.equals(dao.getAccessItem(accessItem.getAccessId())));
	}
	
	@Test
	public void testGetAllAccessItems() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setName("testUser" + i);
			user.setPassword("secret");
			uDao.saveUser(user);
			File file = new File();
			file.setPath("/home/julian/");
			file.setName("test" + i + ".txt");
			file.setOwner(user);
			fDao.saveFile(file);
			AccessItem accessItem = new AccessItem();
			accessItem.setPermissions("rwx");
			accessItem.setUser(user);
			accessItem.setFile(file);
			dao.saveAccessItem(accessItem);
			user.addAccessItem(accessItem);
			uDao.updateUser(user);
			file.addAccessItem(accessItem);
			fDao.updateFile(file);
		}
		assertTrue(dao.getAccessItems().size() >= 10);
	}
	
	@Test
	public void testGetAccessItemsByUser() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		User user = new User();
		user.setName("testUser01");
		user.setPassword("secret");
		uDao.saveUser(user);
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test01.txt");
		file.setOwner(user);
		fDao.saveFile(file);
		AccessItem accessItem = new AccessItem();
		accessItem.setPermissions("rw-");
		accessItem.setUser(user);
		accessItem.setFile(file);
		dao.saveAccessItem(accessItem);
		assertTrue(accessItem.equals(dao.getAccessItem(user).get(0)));
	}
	
	@Test
	public void testGetAccessItemsByFile() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		User user = new User();
		user.setName("testUser02");
		user.setPassword("secret");
		uDao.saveUser(user);
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test02.txt");
		file.setOwner(user);
		fDao.saveFile(file);
		AccessItem accessItem = new AccessItem();
		accessItem.setPermissions("r-x");
		accessItem.setUser(user);
		accessItem.setFile(file);
		dao.saveAccessItem(accessItem);
		assertTrue(accessItem.equals(dao.getAccessItem(file).get(0)));
	}
	
	@Test
	public void testUpdateAccessItem() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		User user = new User();
		user.setName("testUser04");
		user.setPassword("secret");
		uDao.saveUser(user);
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test04.txt");
		file.setOwner(user);
		fDao.saveFile(file);
		AccessItem accessItem = new AccessItem();
		accessItem.setPermissions("rwx");
		accessItem.setUser(user);
		accessItem.setFile(file);
		dao.saveAccessItem(accessItem);
		assertTrue(accessItem.equals(dao.getAccessItem(file).get(0)));
		accessItem.setPermissions("r--");
		dao.updateAccessItem(accessItem);
		assertEquals("r--", dao.getAccessItem(accessItem.getAccessId()).getPermissions());
	}
	
	@Test
	public void testDeleteAccessItem() {
		AccessItemDao dao = AccessItemDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		FileDao fDao = FileDao.getInstance();
		User user = new User();
		user.setName("testUser05");
		user.setPassword("secret");
		uDao.saveUser(user);
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test05.txt");
		file.setOwner(user);
		fDao.saveFile(file);
		AccessItem accessItem = new AccessItem();
		accessItem.setPermissions("rwx");
		accessItem.setUser(user);
		accessItem.setFile(file);
		dao.saveAccessItem(accessItem);
		assertTrue(accessItem.equals(dao.getAccessItem(file).get(0)));
		dao.deleteAccessItem(accessItem);
		assertNull(dao.getAccessItem(accessItem.getAccessId()));
	}
}
