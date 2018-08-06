package com.simpleshare.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

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
			List<AccessItem> accessItems = dao.getAccessItems();
			assertTrue(accessItems.size() >= 10);
			System.out.println(accessItem.getAccessId());
		}
	}
}
