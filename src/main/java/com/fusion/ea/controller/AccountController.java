package com.fusion.ea.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fusion.ea.entity.Account;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.validators.AccountValidator;

@Controller
public class AccountController {

	@Autowired
	AccountService accService;

	@RequestMapping("/master/account/manage")
	public String manageAccount(Model model,HttpServletRequest request) {
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			model.addAttribute("account", new Account());
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.account.manage";
	}

	@RequestMapping(value = { "/master/account/manage",
			"/master/account/populate-for-edit/{id}" }, method = RequestMethod.POST)
	public String saveOrUpdateAccount(
			@ModelAttribute("account") Account account, BindingResult result, Model model) {
		try {
			//validate
			AccountValidator validator = new AccountValidator(accService);
			validator.validate(account, result);
			if(result.hasErrors()) {
				model.addAttribute("showForm", true);
				model.addAttribute("account", account);
				model.addAttribute("accounts", accService.findAll());
				return "master.account.manage";
			}
			account.setOpeningBalance(Double.parseDouble(account
					.getOpeningBalanceString().replaceAll(",", "")));
			if (account.getId() == null) {
				accService.save(account);
			} else {
				accService.update(account);
			}
			model.addAttribute("showForm", false);
			model.addAttribute("account", new Account());
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.account.manage";
	}

	@RequestMapping("/master/account/populate-for-edit/{id}")
	public String populateForEdit(@PathVariable int id, Model model) {
		try {
			Account acc = accService.findOne(id);
			acc.setOpeningBalanceString(String.valueOf(acc.getOpeningBalance()));
			model.addAttribute("account", acc);
			model.addAttribute("showForm", true);
			model.addAttribute("accounts", accService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.account.manage";
	}

	@RequestMapping("/master/account/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id, Model model) {
		JSONObject object = new JSONObject();
		try {
			accService.delete(accService.findOne(id));
			model.addAttribute("showForm", false);
			model.addAttribute("account", new Account());
			model.addAttribute("accounts", accService.findAll());
			object.put("say", "ok");
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}
	
	@RequestMapping("/master/account/get-all")
	public @ResponseBody String getAllByFile() {
		JSONArray array = new JSONArray();
		try {
			List<Account> accounts = accService.findAll();
			for(Account acc : accounts) {
				array.put(acc.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject object = new JSONObject();
		object.put("accounts", array);
		return object.toString();
	}
	
	@RequestMapping("/master/account/get-cash-bank")
	public @ResponseBody String getCashBankByFile() {
		JSONArray array = new JSONArray();
		try {
			List<Account> accounts = accService.findCashBank();
			for(Account acc : accounts) {
				array.put(acc.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject object = new JSONObject();
		object.put("cashBankAccounts", array);
		return object.toString();
	}

	@ModelAttribute("account")
	public Account construct() {
		Account account = new Account();
		return account;
	}

}
