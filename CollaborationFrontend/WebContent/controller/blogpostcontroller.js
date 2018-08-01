/**
 * BlogPostController
 */
app.controller('BlogPostController',function(BlogPostService,$scope,$location){
	
	BlogPostService.getBlogPostsWaitingForApproval().then(function(response){
		$scope.blogPostsWaitingForApproval=response.data;
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	BlogPostService.getBlogPostsApproved().then(function(response){
		$scope.blogPostsApproved=response.data
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	
	$scope.addBlogPost=function(){
		BlogPostService.addBlogPost($scope.blogPost).then(function(response){
			console.log(response.status)
			alert('BlogPost added successfully.. It is waiting for approval')
			$location.path('/getallblogs')
		},function(response){
			if(response.status==401)
				$location.path('/login')
			$location.path('/saveblogpost')
		})
	}
})