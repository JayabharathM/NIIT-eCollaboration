package com.niit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.Job;

public class JobTestCase {
	
	@Autowired
	private static JobDao jobDao;
	
	@BeforeClass
	public static void initialize()
	{
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();		
		jobDao=(JobDao)context.getBean("jobDao");
	}
	
	@Test
	public void addJob()
	{
		Job job=new Job();
		job.setJobTitle("Developer");
		job.setJobDescription("Software Engineer");
		job.setSkillRequired("Java");
		job.setYrsOfExp("3 Years");
		job.setSalary("30000");
		job.setCompanyname("Accenture");
		
		jobDao.saveJob(job);
	}

}
