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
import com.fusion.ea.entity.Charge;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.service.ChargeService;
import com.fusion.ea.validators.ChargeValidator;

@Controller
public class ChargeController {

	@Autowired
	private ChargeService chargeService;
	
	@Autowired
	private AccountService accService;

	@RequestMapping("/master/charge/manage")
	public String manageCharge(Model model,HttpServletRequest request) {
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("charge", new Charge());
			model.addAttribute("charges", chargeService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.charge.manage";
	}

	@RequestMapping(value = { "/master/charge/manage",
			"/master/charge/populate-for-edit/{id}" }, method = RequestMethod.POST)
	public String saveOrUpdateCharge(@ModelAttribute("charge") Charge charge
			,@RequestParam("accountId")String accountId, BindingResult result, Model model) {
		try {
			Account pa = accService.findOne(Integer.parseInt(accountId));
			charge.setPostAccount(pa);

			//validate
			ChargeValidator validator = new ChargeValidator(chargeService);
			validator.validate(charge, result);
			if(result.hasErrors()) {
				model.addAttribute("showForm", true);
				model.addAttribute("charge", charge);
				model.addAttribute("charges", chargeService.findAll());
				model.addAttribute("accounts", accService.findAll());
				return "master.charge.manage";
			}
			
			if (charge.getId() == null) {
				chargeService.save(charge);
			} else {
				chargeService.update(charge);
			}
			model.addAttribute("showForm", false);
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("charge", new Charge());
			model.addAttribute("charges", chargeService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.charge.manage";
	}

	@RequestMapping("/master/charge/populate-for-edit/{id}")
	public String populateForEdit(@PathVariable int id, Model model) {
		try {
			model.addAttribute("charge", chargeService.findOne(id));
			model.addAttribute("showForm", true);
			model.addAttribute("charges", chargeService.findAll());
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.charge.manage";
	}

	@RequestMapping("/master/charge/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id, Model model) {
		JSONObject object = new JSONObject();
		try {
			chargeService.delete(chargeService.findOne(id));
			model.addAttribute("showForm", false);
			model.addAttribute("charge", new Charge());
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("charges", chargeService.findAll());
			object.put("say", "ok");
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@ModelAttribute("charge")
	public Charge construct() {
		Charge charge = new Charge();
		return charge;
	}
	
}
