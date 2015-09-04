package com.fusion.ea.controller;

import java.util.Locale;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.AccountGroup;
import com.fusion.ea.service.AccountGroupService;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.validators.AccountGroupValidator;

@Controller
public class AccountGroupController {

	@Autowired
	private AccountGroupService accGroupService;
	
	@Autowired
	private AccountService accService;

	@Autowired
	private MessageSource msgSource;

	@RequestMapping("/master/account-group/manage")
	public String accountGroup(Model model) {
		try {
			model.addAttribute("showTagForm",false);
			model.addAttribute("groups", accGroupService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.account-group.manage";
	}

	@RequestMapping("/master/account-group/save")
	public @ResponseBody String saveOrUpdateGroup(
			@RequestParam("id") String id,
			@RequestParam("description") String description,
			@RequestParam("type") String type) {
		JSONObject object = new JSONObject();
		try {
			// prepare account group
			AccountGroup group = null;
			if (id.equals("undefined")) {
				group = new AccountGroup();
			} else {
				group = accGroupService.findById(Integer.parseInt(id));
			}
			group.setDescription(description);
			if (type.equals("P")) {
				group.setProfitLoss(true);
			} else {
				group.setProfitLoss(false);
			}

			// validate
			AccountGroupValidator validator = new AccountGroupValidator(
					accGroupService);
			DataBinder binder = new DataBinder(group);
			binder.setValidator(validator);
			binder.validate();
			BindingResult result = binder.getBindingResult();
			if (result.hasErrors()) {
				// invalid inputs
				object.put("say", "invalid-inputs");
				for (FieldError err : result.getFieldErrors()) {
					object.put(err.getField(), msgSource.getMessage(
							err.getCode(), null, Locale.US));
				}
			} else {
				// save
				if (group.getId() != null) {
					group = accGroupService.update(group);
				} else {
					group = accGroupService.save(group);
				}
				object.put("say", "ok");
				object.put("id", group.getId().toString());
			}

		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@RequestMapping("/master/account-group/delete/{id}")
	public @ResponseBody String deleteGroup(@PathVariable int id) {
		JSONObject object = new JSONObject();
		try {
			AccountGroup group = accGroupService.findById(id);
			accGroupService.delete(group);
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
	@RequestMapping("/master/account-group/manageTag")
	public String manageTag(Model model) {
		try {
			model.addAttribute("showTagForm",true);
			model.addAttribute("accounts", accService.findAll());
			model.addAttribute("groups", accGroupService.findAll());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "master.account-group.manage";
	}
	
	@RequestMapping("/master/account-group/tag")
	public String tag(Model model, @RequestParam("accountId") String accountId
			,@RequestParam("groupId") String groupId) {
		try {
			
			Account acc = accService.findOne(Integer.parseInt(accountId));
			AccountGroup accGroup = accGroupService.findById(Integer.parseInt(groupId));
			
			if(accGroup!=null) {
				acc.setAccountGroup(accGroup);
				accService.updateForTag(acc);
			}
			
			model.addAttribute("showTagForm",false);
			model.addAttribute("groups", accGroupService.findAll());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "master.account-group.manage";
	}

	@RequestMapping("/master/account-group/get-group-by-account")
	public @ResponseBody String getGroupByAccount(Model model
			, @RequestParam("accountId")String accountId) {
		JSONObject object = new JSONObject();
		try {
			Account acc = accService.findOne(Integer.parseInt(accountId));
			object.put("say", "ok");
			if(acc.getAccountGroup()==null) {
				object.put("groupId", "-1");
			} else {
				object.put("groupId", acc.getAccountGroup().getId().toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
}
