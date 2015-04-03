package com.fusion.ea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SaleController {

	@RequestMapping("transaction/sale/manage")
	public String manageSaleEntries(){
		return "transaction.sale.manage";
	}
	
}
