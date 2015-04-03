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

import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.Vat;
import com.fusion.ea.service.ItemService;
import com.fusion.ea.service.VatService;
import com.fusion.ea.validators.ItemValidator;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private VatService vatService;

	@RequestMapping("/master/item/manage")
	public String manageItem(Model model,HttpServletRequest request) {
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			model.addAttribute("item", new Item());
			model.addAttribute("items", itemService.findAll());
			model.addAttribute("vatsForSale", vatService.findAllForSale());
			model.addAttribute("vatsForPurchase", vatService.findAllForPurchase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.item.manage";
	}

	@RequestMapping(value = { "/master/item/manage",
			"/master/item/populate-for-edit/{id}" }, method = RequestMethod.POST)
	public String saveOrUpdateItem(
			@ModelAttribute("item") Item item, BindingResult result, Model model,
			@RequestParam("purchaseVatId")String purchaseVatId,
			@RequestParam("saleVatId")String saleVatId) {
		try {
			Vat purchaseVat = vatService.findOne(Integer.parseInt(purchaseVatId));
			Vat saleVat = vatService.findOne(Integer.parseInt(saleVatId));
			
			//validate
			ItemValidator validator = new ItemValidator(itemService);
			validator.validate(item, result);
			if(result.hasErrors()) {
				model.addAttribute("showForm", true);
				model.addAttribute("item", item);
				model.addAttribute("items", itemService.findAll());
				model.addAttribute("vatsForSale", vatService.findAllForSale());
				model.addAttribute("vatsForPurchase", vatService.findAllForPurchase());
				return "master.item.manage";
			}
			
			item.setPurchaseVat(purchaseVat);
			item.setSaleVat(saleVat);
			item.setPurchaseRate(Double.parseDouble(item
					.getPurchaseRateString().replaceAll(",", "")));
			item.setSaleRate(Double.parseDouble(item
					.getSaleRateString().replaceAll(",", "")));
			item.setOpeningQty(Double.parseDouble(item
					.getOpeningQtyString().replaceAll(",", "")));
			item.setOpeningValue(Double.parseDouble(item
					.getOpeningValueString().replaceAll(",", "")));
			
			if (item.getId() == null) {
				itemService.save(item);
			} else {
				itemService.update(item);
			}
			model.addAttribute("showForm", false);
			model.addAttribute("item", new Item());
			model.addAttribute("items", itemService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.item.manage";
	}

	@RequestMapping("/master/item/populate-for-edit/{id}")
	public String populateItemForEdit(@PathVariable int id, Model model) {
		try {
			Item item = itemService.findOne(id);
			item.setPurchaseRateString(String.valueOf(item.getPurchaseRate()));
			item.setSaleRateString(String.valueOf(item.getSaleRate()));
			item.setOpeningQtyString(String.valueOf(item.getOpeningQty()));
			item.setOpeningValueString(String.valueOf(item.getOpeningValue()));
			model.addAttribute("item", item);
			model.addAttribute("showForm", true);
			model.addAttribute("items", itemService.findAll());
			model.addAttribute("vatsForSale", vatService.findAllForSale());
			model.addAttribute("vatsForPurchase", vatService.findAllForPurchase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "master.item.manage";
	}

	@RequestMapping("/master/item/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id, Model model) {
		JSONObject object = new JSONObject();
		try {
			itemService.delete(itemService.findOne(id));
			model.addAttribute("showForm", false);
			model.addAttribute("item", new Item());
			model.addAttribute("items", itemService.findAll());
			model.addAttribute("vatsForSale", vatService.findAllForSale());
			model.addAttribute("vatsForPurchase", vatService.findAllForPurchase());
			object.put("say", "ok");
		} catch (Exception e) {
			object.put("say", "error");
			e.printStackTrace();
		}
		return object.toString();
	}

	@ModelAttribute("item")
	public Item construct() {
		Item item = new Item();
		return item;
	}
	
}
