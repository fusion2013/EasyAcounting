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

import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.ItemGroup;
import com.fusion.ea.service.ItemGroupService;
import com.fusion.ea.service.ItemService;
import com.fusion.ea.validators.ItemGroupValidator;

@Controller
public class ItemGroupController {

	@Autowired
	private ItemGroupService itemGroupService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private MessageSource msgSource;

	@RequestMapping("/master/item-group/manage")
	public String itemGroup(Model model) {
		try {
			model.addAttribute("showTagForm",false);
			model.addAttribute("groups", itemGroupService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.item-group.manage";
	}

	@RequestMapping("/master/item-group/save")
	public @ResponseBody String saveOrUpdateItemGroup(
			@RequestParam("id") String id,
			@RequestParam("description") String description) {
		JSONObject object = new JSONObject();
		try {
			// prepare item group
			ItemGroup group = null;
			if (id.equals("undefined")) {
				group = new ItemGroup();
			} else {
				group = itemGroupService.findById(Integer.parseInt(id));
			}
			group.setDescription(description);
			
			// validate
			ItemGroupValidator validator = new ItemGroupValidator(
					itemGroupService);
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
					group = itemGroupService.update(group);
				} else {
					group = itemGroupService.save(group);
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

	@RequestMapping("/master/item-group/delete/{id}")
	public @ResponseBody String deleteGroup(@PathVariable int id) {
		JSONObject object = new JSONObject();
		try {
			ItemGroup group = itemGroupService.findById(id);
			itemGroupService.delete(group);
			object.put("say", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
	@RequestMapping("/master/item-group/manageTag")
	public String manageTag(Model model) {
		try {
			model.addAttribute("showTagForm",true);
			model.addAttribute("items", itemService.findAll());
			model.addAttribute("groups", itemGroupService.findAll());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "master.item-group.manage";
	}
	
	@RequestMapping("/master/item-group/tag")
	public String tag(Model model, @RequestParam("itemId") String itemId
			,@RequestParam("groupId") String groupId) {
		try {
			
			Item item = itemService.findOne(Integer.parseInt(itemId));
			ItemGroup itemGroup = itemGroupService.findById(Integer.parseInt(groupId));
			
			if(itemGroup!=null) {
				item.setItemGroup(itemGroup);
				itemService.updateForTag(item);
			}
			
			model.addAttribute("showTagForm",false);
			model.addAttribute("groups", itemGroupService.findAll());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "master.item-group.manage";
	}

	@RequestMapping("/master/item-group/get-group-by-item")
	public @ResponseBody String getGroupByItem(Model model
			, @RequestParam("itemId")String itemId) {
		JSONObject object = new JSONObject();
		try {
			Item item = itemService.findOne(Integer.parseInt(itemId));
			object.put("say", "ok");
			if(item.getItemGroup()==null) {
				object.put("groupId", "-1");
			} else {
				object.put("groupId", item.getItemGroup().getId().toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			object.put("say", "error");
		}
		return object.toString();
	}
	
}
