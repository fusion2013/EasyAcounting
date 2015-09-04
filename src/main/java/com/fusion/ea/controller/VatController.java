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
import com.fusion.ea.entity.Vat;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.service.VatService;
import com.fusion.ea.validators.VatValidator;

@Controller
public class VatController {

	@Autowired
	private VatService vatService;
	
	@Autowired
	private AccountService accService;

	@RequestMapping("/master/vat/manage")
	public String manageVat(Model model,HttpServletRequest request) {
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("vat", new Vat());
			model.addAttribute("vats", vatService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.vat.manage";
	}

	@RequestMapping(value = { "/master/vat/manage",
			"/master/vat/populate-for-edit/{id}" }, method = RequestMethod.POST)
	public String saveOrUpdateVat(@ModelAttribute("vat") Vat vat
			,@RequestParam("accountId")String accountId, BindingResult result, Model model) {
		try {
			Account pa = accService.findOne(Integer.parseInt(accountId));
			vat.setPostAccount(pa);

			//validate
			VatValidator validator = new VatValidator(vatService);
			validator.validate(vat, result);
			if(result.hasErrors()) {
				model.addAttribute("showForm", true);
				model.addAttribute("vat", vat);
				model.addAttribute("vats", vatService.findAll());
				model.addAttribute("accounts", accService.findAll());
				return "master.vat.manage";
			}
			
			if (vat.getId() == null) {
				vatService.save(vat);
			} else {
				vatService.update(vat);
			}
			model.addAttribute("showForm", false);
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("vat", new Vat());
			model.addAttribute("vats", vatService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.vat.manage";
	}

	@RequestMapping("/master/vat/populate-for-edit/{id}")
	public String populateForEdit(@PathVariable int id, Model model) {
		try {
			model.addAttribute("vat", vatService.findOne(id));
			model.addAttribute("showForm", true);
			model.addAttribute("vats", vatService.findAll());
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.vat.manage";
	}

	@RequestMapping("/master/vat/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id, Model model) {
		JSONObject object = new JSONObject();
		try {
			vatService.delete(vatService.findOne(id));
			model.addAttribute("showForm", false);
			model.addAttribute("vat", new Vat());
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("vats", vatService.findAll());
			object.put("say", "ok");
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@ModelAttribute("vat")
	public Vat construct() {
		Vat vat = new Vat();
		return vat;
	}
	
}
