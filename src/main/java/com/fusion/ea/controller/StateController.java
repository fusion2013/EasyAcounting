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

import com.fusion.ea.entity.State;
import com.fusion.ea.service.StateService;
import com.fusion.ea.validators.StateValidator;

@Controller
public class StateController {

	@Autowired
	private StateService stateService;

	@Autowired
	private MessageSource msgSource;

	@RequestMapping("/master/state/manage")
	public String manageState(Model model) {
		try {
			model.addAttribute("states", stateService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.state.manage";
	}

	@RequestMapping("/master/state/save")
	public @ResponseBody String saveOrUpdateState(
			@RequestParam("id") String id,
			@RequestParam("name") String name) {
		JSONObject object = new JSONObject();
		try {
			// prepare account state
			State state = null;
			if (id.equals("undefined")) {
				state = new State();
			} else {
				state = stateService.findById(Integer.parseInt(id));
			}
			state.setName(name);
			
			// validate
			StateValidator validator = new StateValidator(
					stateService);
			DataBinder binder = new DataBinder(state);
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
				if (state.getId() != null) {
					state = stateService.update(state);
				} else {
					state = stateService.save(state);
				}
				object.put("say", "ok");
				object.put("id", state.getId().toString());
			}

		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@RequestMapping("/master/state/delete/{id}")
	public @ResponseBody String deleteState(@PathVariable int id) {
		JSONObject object = new JSONObject();
		try {
			State state = stateService.findById(id);
			stateService.delete(state);
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
}
