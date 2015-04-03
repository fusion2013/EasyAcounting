package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Company;

public class CompanyProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Company.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		// TODO Auto-generated method stub
		try {
			Company company = (Company) obj;
			if (company.getName() == null
					|| company.getName().trim().equals("")) {
				error.rejectValue("name", "companyprofile.name.empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
