package com.niit.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.model.Chat;

/*package com.niit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.dao.UserDao;
import com.niit.model.Chat;

@Controller
public class SockController 
{
	private SimpMessagingTemplate messagingTemplate;
	//username will be added to the list whenever user joins the chat room
	
	private List<String> users=new ArrayList<String>();
	
	//The object of type MessagingTemplate will be injected[autowired] by spring container
    @Autowired
	public SockController(SimpMessagingTemplate messagingTemplate) {
		super();
		this.messagingTemplate = messagingTemplate;
	}
    //$scope.stompClient.subscribe("app/join/$scope.userName")
    
    @SubscribeMapping("/join/{username}")
    public List<String> join(@DestinationVariable("username") String username)
    {
    	if(!users.contains(username))
    		users.add(username);
    	messagingTemplate.convertAndSend("/topic/join",username);
    	return users;
    }
    
    @MessageMapping("/chat")
   public void chatReceived(Chat chat){
    	
    	System.out.println("From value in chatreceived in chat object " + chat.getFrom());

		System.out.println(chat.getFrom());
		
	   if("all".equals(chat.getTo())){
		   messagingTemplate.convertAndSend("/queue/chats",chat);
	   }
	   else{
		   System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());
		   
		   messagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(),chat);
		   messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
		   
	   }
   }
}
*/


@Controller
public class SockController {

	private static final Log logger = LogFactory.getLog(SockController.class);

	private final SimpMessagingTemplate messagingTemplate;

	//username will be added to the list whenever user joins the chat room
	private List<String> users = new ArrayList<String>();

	//The object of type MessagingTemplate will be injected[autowired] by spring container
	@Autowired
	public SockController(SimpMessagingTemplate messagingTemplate) {

		this.messagingTemplate = messagingTemplate;

	}


	// when user joins the chat room - $scope.stompClient.subscribe("app/join/$scope.username) 
	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable("username") String username) {
        

		 System.out.println("username in sockcontroller" + username);
		 
		 if(!users.contains(username)) {
				users.add(username);
			}


		System.out.println("====JOIN==== " + username);

		// notify all subscribers of new user

		messagingTemplate.convertAndSend("/topic/join", username);

		return users;

	}
	
	// when users' send and receive messages ( chat ) to each other :
		/*
		 * 	@MessageMapping - handles STOMP (Simple Text Oriented Message Protocol) messages within the springMvc
		 * 				and is enabled by @EnableWebSocketMessageBroker
		 */
    @MessageMapping(value = "/chat")
    public void chatReveived(Chat chat) {


		if ("all".equals(chat.getTo())) {

			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());

			messagingTemplate.convertAndSend("/queue/chats", chat);

		}

		else {

			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getTo(), chat);

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getFrom(), chat);

		}

	}

}