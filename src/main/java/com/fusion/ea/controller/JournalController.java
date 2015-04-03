package com.fusion.ea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JournalController {

	@RequestMapping("transaction/journal/manage")
	public String manageJournalEntries(){
		return "transaction.journal.manage";
	}
	
}
