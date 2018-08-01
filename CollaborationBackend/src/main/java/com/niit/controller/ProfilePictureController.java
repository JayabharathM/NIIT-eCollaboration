package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.model.Error;
import com.niit.model.ProfilePicture;

@Controller
public class ProfilePictureController 
{
	@Autowired
	private ProfilePictureDao profilePictureDao;
	
	@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePic(@RequestParam CommonsMultipartFile image,HttpSession session)
    {
		System.out.println("hello");
    	if(session.getAttribute("username")==null)
		{
			Error error = new Error(5,"Unauthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
    	String username=(String)session.getAttribute("username");
    	ProfilePicture profilePicture=new ProfilePicture();
    	profilePicture.setUsername(username);
    	profilePicture.setImage(image.getBytes());
    	try{
    		profilePictureDao.saveProfilePicture(profilePicture);
    		return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
    	}catch(Exception e){
    		Error error=new Error(6,"Unable to upload profile picture");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
	
	
	
	@RequestMapping(value="/getimage/{username}",method=RequestMethod.GET)
	public @ResponseBody byte[] getImage(@PathVariable String username,HttpSession session)
	{
		if(session.getAttribute("username")==null)
		{
		  return null;
		}
		else{
			ProfilePicture profilePicture=profilePictureDao.getProfilePicture(username);
			if(profilePicture==null)
				return null;
			return profilePicture.getImage();
		}
	}
}
