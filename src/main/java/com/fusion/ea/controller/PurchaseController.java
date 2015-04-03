package com.fusion.ea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PurchaseController {

	@RequestMapping("transaction/purchase/manage")
	public String managePurchaseEntries(Model model){
		return "transaction.purchase.manage";
	}
	
}
