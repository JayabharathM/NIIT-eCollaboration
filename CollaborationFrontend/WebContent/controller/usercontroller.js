
/**
 * UserController
 */
app.controller('UserController',function(UserService,$scope,$rootScope,$location,$cookieStore){
	$scope.user={}
	
	if($rootScope.currentUser!=undefined){
		UserService.getUser().then(function(response){
			$scope.user=response.data;
		},function(response){
			console.log(response.status)
			$location.path('/login') //changes i have made
	})
}
	
	$scope.registerUser=function(){
		UserService.registerUser($scope.user).then(function(response){
			$rootScope.message="Registered successfully.. please login again"
			$location.path('/login')
		},function(response){
			console.log(response.status)
			console.log(response.data)
			$scope.error=response.data
			$location.path('/register')
		})
	}
	$scope.validateUser=function(){
	UserService.validateUser($scope.user).then(function(response){
		console.log(response.data)
		$rootScope.currentUser=response.data//{{username , password, email and so on ...
		$cookieStore.put("currentUser",response.data)
		$location.path('/home')
		
	},function(response){
		$scope.error=response.data
		console.log(response.status)
		$location.path('/login')
		
	})
  }
	
	
	$scope.updateUser=function(){
		UserService.updateUser($scope.user).then(function(response){
			alert("updated successfully")
			$location.path('/home')
		},function(response){
			console.log(response.data)
			/*
			 * For unauthorised access, 401 -> redirect the user page to login page
			 * For exception , 500 ->redirect the user to the updateprofile page
			 */
			if(response.status==401)
			$location.path('/login')
			$location.path('/editprofile')
		})
		
	}
	
})
	