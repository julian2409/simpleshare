package com.simpleshare.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.simpleshare.PrepareTests;
import com.simpleshare.model.User;

public class UserDaoTest {

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
	public void testGetInstance() {
		UserDao dao = UserDao.getInstance();
		assertNotNull(dao);
	}
	
	@Test
	public void testGetAllUsers() {
		UserDao dao = UserDao.getInstance();
		User user;
		for (int i = 0; i < 10; i++) {
			user = new User();
			user.setName("testUser" + i);
			user.setPassword("secret");
			dao.saveUser(user);
		}
		List<User> users = dao.getUsers();
		assertTrue(users.size() >= 10);
	}
	
	@Test
	public void testFindUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setName("testUser");
		user.setPassword("secret");
		user = dao.saveUser(user);
		User persistedUser = dao.findUser(user.getUserId());
		assertTrue(user.equals(persistedUser));
	}
	
	@Test
	public void testSaveUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setName("testUserC");
		user.setPassword("secret");
		user = dao.saveUser(user);
		User persistedUser = dao.findUser(user.getUserId());
		assertTrue(user.equals(persistedUser));
	}
	
	@Test
	public void testFindUserByName() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		String userName = "testUserA";
		user.setName(userName);
		user.setPassword("secret");
		dao.saveUser(user);
		User persistedUser = dao.findUserByName(userName);
		assertTrue(user.equals(persistedUser));
	}
	
	@Test
	public void testDeleteUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setName("testUserB");
		user.setPassword("secret");
		int id = dao.saveUser(user).getUserId();
		assertNotNull(dao.findUser(id));
		dao.deleteUser(user);
		assertNull(dao.findUser(id));
	}
	
	@Test
	public void testUpdateUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setName("original");
		user.setPassword("original_pwd");
		int id = dao.saveUser(user).getUserId();
		user = dao.findUser(id);
		user.setPassword("modified_pwd");
		user.setName("modified");
		dao.updateUser(user);
		assertEquals(dao.findUser(id).getUserName(), "modified");
		assertEquals(dao.findUser(id).getPassword(), "modified_pwd");
	}
}
