package com.fusion.ea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReceiptController {

	@RequestMapping("transaction/receipt/manage")
	public String manageReceiptEntries(){
		return "transaction.receipt.manage";
	}
	
}
