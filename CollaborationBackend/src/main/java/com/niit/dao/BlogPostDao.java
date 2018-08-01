package com.niit.dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

public interface BlogPostDao 
{

	void saveBlogPost(BlogPost blogPost);

	List<BlogPost> getBlogPosts(int approved);
	
	BlogPost getBlogPostById(int id);

	void updateBlogPost(BlogPost blogPost);

	void addBlogcomment (BlogComment blogComment);
	
	List<BlogComment> getAllBlogComments(int blogPostId);

}
