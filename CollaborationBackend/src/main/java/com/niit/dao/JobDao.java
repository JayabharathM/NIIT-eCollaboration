package com.niit.dao;

import java.util.List;

import com.niit.model.Job;

public interface JobDao {

	void saveJob(Job job);

	List<Job> getAllJobs();

	Job getJobById(int job);

	

}
