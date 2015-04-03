package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.User;

public class UserProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			User user = (User) obj;
			if (user.getFirstName() == null
					|| user.getFirstName().trim().equals("")) {
				errors.rejectValue("firstName", "userprofile.firstName.empty");
			}
			if (user.getLastName() == null
					|| user.getLastName().trim().equals("")) {
				errors.rejectValue("lastName", "userprofile.lastName.empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
