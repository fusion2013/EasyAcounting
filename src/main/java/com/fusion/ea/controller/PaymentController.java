package com.fusion.ea.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.Payment;
import com.fusion.ea.entity.PaymentAccount;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.service.PaymentService;
import com.fusion.ea.validators.AccountValidator;
import com.fusion.ea.validators.PaymentValidator;

@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private AccountService accService;
	
	@RequestMapping("/transaction/payment/manage")
	public String managePaymentEntries(Model model,HttpServletRequest request){
		try {
			if(request.getQueryString()!=null) {
				model.addAttribute("showForm", true);
			} else {
				model.addAttribute("showForm", false);
			}
			Payment p = new Payment();
			PaymentAccount pa = new PaymentAccount();
			List<PaymentAccount> pas = new ArrayList<PaymentAccount>();
			pas.add(pa);
			p.setPaymentAccounts(pas);
			model.addAttribute("payment", p);
			model.addAttribute("payments", paymentService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "transaction.payment.manage";
	}
	
	@RequestMapping(value ="/transaction/payment/manage", method = RequestMethod.POST)
	public String saveOrUpdateAccount(
			@ModelAttribute("payment") Payment payment, Model model, BindingResult result,
			HttpServletRequest request) {
		try {
			//validate
//			PaymentValidator validator = new PaymentValidator(paymentService, accService, partyList);
//			validator.validate(payment, result);
//			if(result.hasErrors()) {
//				model.addAttribute("showForm", true);
//				model.addAttribute("account", payment);
//				model.addAttribute("payments", paymentService.findAll());
//				return "transaction.payment.manage";
//			}
			
			//set values
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			Date entryDate = sdf.parse(payment.getDateString());
			Account cashBank = accService.findByName(payment.getCashBankString());
			double total = 0;
			for(PaymentAccount pa : payment.getPaymentAccounts()) {
				pa.setAccountId(accService.findByName(pa.getAccountId().getName()));
				double amt = Double.parseDouble(pa.getAmountString().replace(",", ""));
				pa.setAmount(amt);
				pa.setPaymentId(payment);
				total = total + pa.getAmount();
			}
			payment.setTotal(total);
			payment.setDate(entryDate);
			payment.setCashBankAccount(cashBank);
			
			//save paymnet
			paymentService.save(payment);
			
			//models
			Payment p = new Payment();
			PaymentAccount pa = new PaymentAccount();
			List<PaymentAccount> pas = new ArrayList<PaymentAccount>();
			pas.add(pa);
			p.setPaymentAccounts(pas);
			model.addAttribute("payment", p);
			model.addAttribute("showForm", true);
			model.addAttribute("payments", paymentService.findAll());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "transaction.payment.manage";
	}
	
	@RequestMapping("/transaction/payment/populate-for-edit/{id}")
	public String populateForEdit(@PathVariable int id, Model model, HttpServletResponse request) {
		try {
			Payment payment = paymentService.findOne(id);
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
			String dateString = sdf.format(payment.getDate());
			payment.setDateString(dateString);
			model.addAttribute("payment", payment);
			model.addAttribute("showForm", true);
			model.addAttribute("payments", paymentService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "transaction.payment.manage";
	}
	
}
