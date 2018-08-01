/**
 * BlogPostService
 */

app.factory('BlogPostService',function($http){
	var blogPostService={};
	
	blogPostService.addBlogPost=function(blogPost){
		return $http.post("http://localhost:8090/CollaborationBackend/saveblogpost",blogPost)
	}
	
	blogPostService.getBlogPostsWaitingForApproval=function(){
		return $http.get("http://localhost:8090/CollaborationBackend/getblogposts/"+0)
	}
	
	blogPostService.getBlogPostsApproved=function(){
		return $http.get("http://localhost:8090/CollaborationBackend/getblogposts/"+1)
	}
	
	blogPostService.getBlogPostById=function(id){
		return $http.get("http://localhost:8090/CollaborationBackend/getblogpostbyid/"+id)
	}
	
	blogPostService.updateBlogPost=function(blogPost){
		return $http.put("http://localhost:8090/CollaborationBackend/updateblogpost",blogPost)
	}
	blogPostService.addComment=function(blogComment){
		return $http.post("http://localhost:8090/CollaborationBackend/addblogcomment",blogComment)
	}
	blogPostService.getBlogComments=function(blogPostId){
		return $http.get("http://localhost:8090/CollaborationBackend/getblogcomments/"+blogPostId)
	}
	return blogPostService;
})