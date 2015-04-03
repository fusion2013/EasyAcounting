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

import com.fusion.ea.entity.Daybook;
import com.fusion.ea.entity.SalePurchase;
import com.fusion.ea.service.DaybookService;
import com.fusion.ea.validators.DaybookValidator;

@Controller
public class DaybookController {

	@Autowired
	private DaybookService daybookService;

	@Autowired
	private MessageSource msgSource;

	@RequestMapping("/master/daybook/manage")
	public String manageDaybook(Model model) {
		try {
			model.addAttribute("daybooks", daybookService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.daybook.manage";
	}

	@RequestMapping("/master/daybook/save")
	public @ResponseBody String saveOrUpdateDaybook(
			@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("type") String type) {
		JSONObject object = new JSONObject();
		try {
			// prepare account daybook
			Daybook daybook = null;
			if (id.equals("undefined")) {
				daybook = new Daybook();
			} else {
				daybook = daybookService.findById(Integer.parseInt(id));
			}
			daybook.setName(name);
			if(type.equals("SALE")) {
				daybook.setType(SalePurchase.SALE);
			} else {
				daybook.setType(SalePurchase.PURCHASE);
			}
			
			// validate
			DaybookValidator validator = new DaybookValidator(
					daybookService);
			DataBinder binder = new DataBinder(daybook);
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
				if (daybook.getId() != null) {
					daybook = daybookService.update(daybook);
				} else {
					daybook = daybookService.save(daybook);
				}
				object.put("say", "ok");
				object.put("id", daybook.getId().toString());
			}

		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@RequestMapping("/master/daybook/delete/{id}")
	public @ResponseBody String deleteDaybook(@PathVariable int id) {
		JSONObject object = new JSONObject();
		try {
			Daybook daybook = daybookService.findById(id);
			daybookService.delete(daybook);
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
}
