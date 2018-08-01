/**
 * FriendController
 */
app.controller('FriendController',function($scope,$location,FriendService){
	/*
	 * data is for listofsuggestedusers.html
	 */
	function listOfSuggestedUsers(){
		FriendService.listOfSuggestedUsers().then(function(response){
			$scope.suggestedusers=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
				console.log(response.status)
		})
	}
	/*
	 * data is for listofpendingrequests.html
	 */
	function listOfPendingRequests(){
		FriendService.listOfPendingRequests().then(function(response){
			$scope.pendingrequests=response.data;
		},function(response){
			if(response.status==401)
				$location.path('/login')
				console.log(response.status)
		})
	}
	
	function listOfFriends(){
		FriendService.listOfFriends().then(function(response){
			$scope.friends=response.data;
		},function(response){
			if(response.status==401)
				$location.path('/login')
				console.log(response.status)
		})
	}
	/*
	 * toId=user.username
	 */
	$scope.friendRequest=function(toId){
		FriendService.friendRequest(toId).then(function(response){
			alert('request has been sent successfully')
		listOfSuggestedUsers();
			$location.path('/suggesteduserslist')
		},function(response){
			if(response.status==401)
				$locatiom.path('/login')
				console.log(response.status)
		})
	}
	
	/*
	 * from HTML page to controller
	 */
	$scope.updatePendingRequest=function(pendingRequest,status){
		/*
		 * pendingRequest.status='P'
		 * pendingRequest.status=status -> assign 'A'/'D'
		 */
		console.log(pendingRequest.status)
		pendingRequest.status=status
		/*
		 * pendingRequest is an object of type Friend
		 * id,fromId,toId and status(A/D)
		 */
		FriendService.updatePendingRequest(pendingRequest).then(function(response){
			listOfPendingRequests();
			$location.path('/pendingrequests')
		},function(response){
			if(response.status==401)
				$location.path('/login')
				console.log(response.status)
		})
	}
	
	/*
	 * for assigning data to scope property,
	 * data will be displayed in the HTML page
	 */
	listOfSuggestedUsers()
	listOfPendingRequests()
	listOfFriends()
})