/**
 * Job Controller
 */
app.controller('JobController', function(JobService,$scope,$location){
	$scope.showJobDetails=false;
	
	function getAllJobs(){
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data;
		}, function(response){
			$location.path('/login')
		})
	}  
	
	
	$scope.saveJob=function(){
		JobService.saveJob($scope.job).then(function(response){
			$location.path('/getallJobs')
		},function(response){
			console.log(response.status)
			if(response.status==401) /* Unauthorised user or access denied */
				$location.path('/login')
		    if(response.status==500){
		    	$scope.error=response.data
		    	$location.path=('/savejob')
		    }
		    $location.path=('/login')	//changes i have made
		})
	}
	
	$scope.getJobDetails=function(job){
		$scope.showJobDetails=true
		JobService.getJobDetails(job).then(function(response){
		//$location.path('/getjobbyid')
			$scope.job=response.data
		},function(response){
			console.log(response.data)
			$location.path('/login')
		})
	}
	
	getAllJobs();
})