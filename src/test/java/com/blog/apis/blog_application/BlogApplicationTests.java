package com.blog.apis.blog_application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.apis.blog_application.repositories.UserRepo;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void repoTest() {
		String className= userRepo.getClass().getName();
		System.out.println(className);

		String packageName =userRepo.getClass().getPackageName();
		System.out.println(packageName);
	}

}
