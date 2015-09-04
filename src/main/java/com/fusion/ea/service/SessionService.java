package com.fusion.ea.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;

@Service
public class SessionService {

	@Autowired
	HttpSession session;
	
	public User getLoggedInUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
	}
	
	public File getSelectedFile() {
		return (File) session.getAttribute("selectedFile");
	}
	
	public void setSelectedFile(File file) {
		session.setAttribute("selectedFile",file);
	}

}
