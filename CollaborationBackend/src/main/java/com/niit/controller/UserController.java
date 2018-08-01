package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.model.User;
import com.niit.dao.UserDao;
import com.niit.model.Error;

@Controller
public class UserController
{
	@Autowired
	private UserDao userDao;
	//? - Any Type
    @RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
    	try{
    	User duplicateUser	=userDao.validateUsername(user.getUsername());
    	if(duplicateUser!=null){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(2,"Username already exists.. please enter different username");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
    	User duplicateEmail=userDao.validateEmail(user.getEmail());
    	if(duplicateEmail!=null){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(3,"Email address already exists.. please enter different email address");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
		userDao.registerUser(user);
		//response.data is user
		//response.status is 200 OK
		return new ResponseEntity<User>(user,HttpStatus.OK);
    	}catch(Exception e){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(1,"Unable to register user details " + e.getMessage());
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	//HttpRequest Body : {username:"adam" , password:"123"}
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session)
	{
		User validuser = userDao.login(user);
		if (validuser==null)//invalid credentials
		{
			Error error = new Error (4, "Invalid username/password..please enter valid username/password");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		validuser.setOnline(true);
		userDao.update(validuser);//update only the online status from 0 to 1
		session.setAttribute("username", validuser.getUsername());
		//HttpResponse Body;
		// {username :"adam" , password:"123", firstname="Adam", lastname="Eve", email :"adam@gmail.com", online_status :"true"
		return new ResponseEntity<User>(validuser, HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username = (String)session.getAttribute("username");
		User user = userDao.getUserByUsername(username);
		user.setOnline(false);
		userDao.update(user);
		session.removeAttribute("username");
        session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		String username=(String)session.getAttribute("username");
		User user=userDao.getUserByUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	/*
	 * Error, 401 - UNAUTHORISED ACCESS
	 * Error, 500 - EXCEPTION
	 * void, 200 - SUCCESSFUL UPDATE
	 */
	
	@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user, HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		try{
			userDao.update(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e){
			Error error = new Error(6,"Unable to edit user profile"+ e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
