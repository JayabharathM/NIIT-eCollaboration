/**
 * UserService
 */
app.factory('UserService',function($http){
	var userService={}; //instantiation
	var BASE_URL="http://localhost:8090/CollaborationBackend/"
	
	userService.registerUser=function(user){
		return $http.post(BASE_URL+"registeruser",user)
	}
	
	userService.validateUser=function(user){
		return $http.post( BASE_URL+"login",user)
	}
	
	userService.logout=function(){
		return $http.get(BASE_URL+"logout")
	}
	
	userService.getUser=function(user){
		return $http.get(BASE_URL+"getuser",user)
	}
	
	
	userService.updateUser=function(user){
		return $http.put(BASE_URL+"/updateuser",user)
	}
	
	return userService; //returning the instance
})
