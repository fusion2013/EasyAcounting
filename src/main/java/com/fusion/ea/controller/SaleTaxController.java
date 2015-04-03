package com.fusion.ea.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.SaleTax;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.service.SaleTaxService;
import com.fusion.ea.validators.SaleTaxValidator;

@Controller
public class SaleTaxController {

	@Autowired
	private SaleTaxService saleTaxService;
	
	@Autowired
	private AccountService accService;

	@RequestMapping("/master/sale-tax/manage")
	public String manageSaleTax(Model model,HttpServletRequest request) {
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("saleTax", new SaleTax());
			model.addAttribute("saleTaxes", saleTaxService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.sale-tax.manage";
	}

	@RequestMapping(value = { "/master/sale-tax/manage",
			"/master/sale-tax/populate-for-edit/{id}" }, method = RequestMethod.POST)
	public String saveOrUpdateSaleTax(@ModelAttribute("saleTax") SaleTax saleTax
			,@RequestParam("accountId")String accountId, BindingResult result, Model model) {
		try {
			Account pa = accService.findOne(Integer.parseInt(accountId));
			saleTax.setPostAccount(pa);

			//validate
			SaleTaxValidator validator = new SaleTaxValidator(saleTaxService);
			validator.validate(saleTax, result);
			if(result.hasErrors()) {
				model.addAttribute("showForm", true);
				model.addAttribute("saleTax", saleTax);
				model.addAttribute("saleTaxes", saleTaxService.findAll());
				model.addAttribute("accounts", accService.findAll());
				return "master.sale-tax.manage";
			}
			
			if (saleTax.getId() == null) {
				saleTaxService.save(saleTax);
			} else {
				saleTaxService.update(saleTax);
			}
			model.addAttribute("showForm", false);
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("saleTax", new SaleTax());
			model.addAttribute("saleTaxes", saleTaxService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.sale-tax.manage";
	}

	@RequestMapping("/master/sale-tax/populate-for-edit/{id}")
	public String populateForEdit(@PathVariable int id, Model model) {
		try {
			model.addAttribute("saleTax", saleTaxService.findOne(id));
			model.addAttribute("showForm", true);
			model.addAttribute("saleTaxes", saleTaxService.findAll());
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.sale-tax.manage";
	}

	@RequestMapping("/master/sale-tax/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id, Model model) {
		JSONObject object = new JSONObject();
		try {
			saleTaxService.delete(saleTaxService.findOne(id));
			model.addAttribute("showForm", false);
			model.addAttribute("saleTax", new SaleTax());
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("saleTaxes", saleTaxService.findAll());
			object.put("say", "ok");
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@ModelAttribute("saleTax")
	public SaleTax construct() {
		SaleTax saleTax = new SaleTax();
		return saleTax;
	}
	
}
