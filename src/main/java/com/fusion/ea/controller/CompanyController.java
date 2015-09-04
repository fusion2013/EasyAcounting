package com.fusion.ea.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fusion.ea.entity.Company;
import com.fusion.ea.entity.User;
import com.fusion.ea.service.CompanyService;
import com.fusion.ea.service.SessionService;
import com.fusion.ea.validators.CompanyProfileValidator;

@Controller
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/company/profile")
	public String companyProfile() {
		return "company.profile";
	}

	@RequestMapping(value = "/company/profile", method = RequestMethod.POST)
	public String saveCompanyProfile(
			@ModelAttribute("company") Company company, BindingResult result) {
		try {
			// validate
			CompanyProfileValidator validator = new CompanyProfileValidator();
			validator.validate(company, result);
			if (result.hasErrors()) {
				return "company.profile";
			}

			//save
			companyService.save(company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "company.profile";
	}

	@ModelAttribute("company")
	public Company construct(HttpServletRequest request) {
		try {
			User user = sessionService.getLoggedInUser();
			Company company = user.getCompany();
			request.setAttribute("admin", user.isAdmin());
			return company;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
