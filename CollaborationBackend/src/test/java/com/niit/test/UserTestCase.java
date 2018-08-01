package com.niit.test;


import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.niit.dao.UserDao;
import com.niit.model.User;

@WebAppConfiguration
public class UserTestCase {
	
	static UserDao userDao;
	
	@BeforeClass
	public static void initialize()
	{
		//AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
		context.scan("com.niit");
		context.refresh();		
		userDao=(UserDao)context.getBean("userDao");
	}
	
	@Test
	public void register()
	{
		User user=new User();
		user.setUsername("jai");
		user.setPassword("pass");
		user.setFirstname("Jaya");
		user.setLastname("Bharath");
		user.setEmail("jayabharath1995@gmail.com");
		user.setPhonenumber("9790795489");
		user.setRole("ROLE_USER");
		//user.setOnline();
		userDao.registerUser(user);
		
		
	}

}
