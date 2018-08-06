package com.simpleshare.dao;

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
import com.simpleshare.model.File;
import com.simpleshare.model.User;

public class FileDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PrepareTests.initDatabase();
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setName("testUser0");
		user.setPassword("secret");
		dao.saveUser(user);
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
		FileDao dao = FileDao.getInstance();
		assertNotNull(dao);
	}
	
	@Test
	public void testSaveFile() {
		FileDao dao = FileDao.getInstance();
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test0.txt");
		file.setOwner(UserDao.getInstance().getUsers().get(0));
		file = dao.saveFile(file);
		File persistedFile = dao.getFile(file.getFileId());
		assertTrue(file.equals(persistedFile));
	}
	
	@Test
	public void testGetFile() {
		FileDao dao = FileDao.getInstance();
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test1.txt");
		User user = UserDao.getInstance().getUsers().get(0);
		file.setOwner(user);
		int id = dao.saveFile(file).getFileId();
		assertTrue(file.equals(dao.getFile(id)));
	}
	
	@Test
	public void testGetFilesForUser() {
		FileDao dao = FileDao.getInstance();
		User user = UserDao.getInstance().getUsers().get(0);
		File file = new File();
		file.setName("test2.txt");
		file.setPath("/home/julian/");
		file.setOwner(user);
		dao.saveFile(file);
		List<File> files = dao.getFilesForUser(user.getUserId());
		assertTrue(files.size() > 0);
	}
	
	@Test
	public void testGetAllFiles() {
		FileDao dao = FileDao.getInstance();
		for (int i = 0; i < 10; i++) {
			File file = new File();
			file.setName("test0" + i + "txt");
			file.setPath("/home/julian/");
			file.setOwner(UserDao.getInstance().getUsers().get(0));
			dao.saveFile(file);
		}
		List<File> files = dao.getAllFiles();
		assertTrue(files.size() >= 10);
	}
	
	@Test
	public void testUpdateFile() {
		FileDao dao = FileDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("test01.txt");
		User user = new User();
		user.setName("testUser01");
		user.setPassword("secret");
		uDao.saveUser(user);
		file.setOwner(user);
		dao.saveFile(file);
		file = dao.getFile(file.getFileId());
		file.setName("updatedTestFile.txt");
		dao.updateFile(file);
		assertTrue(dao.getFile(file.getFileId()).getName().equals("updatedTestFile.txt"));
	}
	
	@Test
	public void testDeleteFile() {
		FileDao dao = FileDao.getInstance();
		UserDao uDao = UserDao.getInstance();
		File file = new File();
		file.setPath("/home/julian/");
		file.setName("testFile001.txt");
		User user = new User();
		user.setName("testUser001");
		user.setPassword("secret");
		uDao.saveUser(user);
		file.setOwner(user);
		dao.saveFile(file);
		assertNotNull(dao.getFile(file.getFileId()));
		dao.deleteFile(file);
		assertNull(dao.getFile(file.getFileId()));
	}
}
