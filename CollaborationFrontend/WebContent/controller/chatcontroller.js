/**
 * ChatController
 */
//frame is an object for protocol
//Websocket supports stomp protocol
app.controller('ChatController', ['$rootScope' ,'$scope', 'socket', function($rootScope ,$scope, socket) {
    alert('entering chat controller')
    $scope.chats = [];//array of chat messages
    $scope.stompClient = socket.stompClient;
    $scope.users=[]//array of users
    
    
    $scope.$on('sockConnected', function(event, frame) {
    	alert('sockconnected')//means websocket is connected
        $scope.userName=$rootScope.currentUser.username;
        $scope.stompClient.subscribe("/topic/join", function(message) {
        	
            user = JSON.parse(message.body);
            console.log(user)
            /*
             * if user is already in users array or name of theuser is same as logged in username then
             * need not add that user in users
             */
           
            if(user != $scope.userName && $.inArray(user, $scope.users) == -1) {
                $scope.addUser(user);
                $scope.latestUser = user;
                $scope.$apply();
                $('#joinedChat').fadeIn(100).delay(2000).fadeOut(200);
            }
            
        });
        /*
         * new user joins the chat room, username has to be send to the application
         * receive list of users already there in that chat room
         */
  
        $scope.stompClient.subscribe('/app/join/'+$scope.userName, function(message) {
        
            $scope.users = JSON.parse(message.body);
        	
            $scope.$apply();
        });
        
    });

    $scope.sendMessage = function(chat) {
        chat.from = $scope.userName;
      
        $scope.stompClient.send("/app/chat", {}, JSON.stringify(chat));
        $rootScope.$broadcast('sendingChat', chat);
        $scope.chat.message = '';

    };

    $scope.capitalize = function(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    };
 
    $scope.addUser = function(user) {
        $scope.users.push(user);
        $scope.$apply();
    };
 
    
    
    
    
    
    
    $scope.$on('sockConnected', function(event, frame) {
        $scope.userName=$rootScope.currentUser.username;
  
        $scope.user=$rootScope.currentUser.username;
       
        $scope.stompClient.subscribe( "/queue/chats/"+$scope.userName, function(message) {
        	
            $scope.processIncomingMessage(message, false);
        });
        
        
        $scope.stompClient.subscribe("/queue/chats", function(message) {
        	
            $scope.processIncomingMessage(message, true);
        });
        
        
    });

    $scope.$on('sendingChat', function(event, sentChat) {
        chat = angular.copy(sentChat);
        chat.from = 'Me';
        chat.direction = 'outgoing';
        $scope.addChat(chat);
    });

    $scope.processIncomingMessage = function(message, isBroadcast) {
        message = JSON.parse(message.body);
        message.direction = 'incoming';
        if(message.from != $scope.userName) {
        	$scope.addChat(message);
            $scope.$apply(); // since inside subscribe closure
        }
    };

 
    $scope.addChat = function(chat) {
        $scope.chats.push(chat);
    };
 
 
}]);