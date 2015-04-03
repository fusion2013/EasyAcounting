package com.fusion.ea.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fusion.ea.entity.User;
import com.fusion.ea.service.SessionService;
import com.fusion.ea.service.UserService;
import com.fusion.ea.validators.UserProfileValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/user/profile")
	public String userProfile() {
		return "user.profile";
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.POST)
	public String saveUserProfile(@ModelAttribute("user") User user,
			BindingResult result) {
		try {
			// validate
			UserProfileValidator validator = new UserProfileValidator();
			validator.validate(user, result);
			if (result.hasErrors()) {
				return "user.profile";
			}

			//save
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			user.setBirthDate(sdf.parse(user.getDay() + "-" + user.getMonth()
					+ "-" + user.getYear()));
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user.profile";
	}

	@ModelAttribute("user")
	public User construct() {
		try {
			User user = sessionService.getLoggedInUser();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String bd = sdf.format(user.getBirthDate());
			String[] bds = bd.split("-");
			user.setDay(bds[0]);
			user.setMonth(bds[1]);
			user.setYear(bds[2]);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
