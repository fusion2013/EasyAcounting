package com.fusion.ea.validators;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Account;
import com.fusion.ea.entity.Payment;
import com.fusion.ea.service.AccountService;
import com.fusion.ea.service.PaymentService;

public class PaymentValidator implements Validator {

	private PaymentService paymentService;
	private AccountService accService;
	private List<String> partyList;

	public PaymentValidator(PaymentService paymentService,
			AccountService accService, List<String> partyList) {
		this.paymentService = paymentService;
		this.accService = accService;
		this.partyList = partyList;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Payment.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			// TODO Auto-generated method stub
			Payment payment = (Payment) target;

			if (payment.getSrNo() == null) {
				errors.rejectValue("srNo", "payment.srno.empty");
			} else if (payment.getSrNo().trim().equals("")) {
				errors.rejectValue("srNo", "payment.srno.empty");
			}

			if (payment.getDateString() == null
					|| payment.getDateString().equals("")) {
				errors.rejectValue("dateString", "payment.dateString.empty");
			}

			if (payment.getCashBankString() == null
					|| payment.getCashBankString().equals("")) {
				errors.rejectValue("cashBankString",
						"payment.cashBankString.empty");
			} else {
				Account acc = accService
						.findByName(payment.getCashBankString());
				if (acc == null || (!acc.isCashBank())) {
					errors.rejectValue("cashBankString",
							"payment.cashBankString.notfound");
				}
			}

//			if (partyList.size() == 0) {
//				errors.rejectValue("paymentAccounts",
//						"payment.paymentAccounts.empty");
//			} else {
//				for (int i = 0; i < partyList.size(); i++) {
//					Account acc = accService.findByName(partyList.get(i));
//					if (acc == null || (!acc.isCashBank())) {
//						errors.rejectValue("paymentAccounts",
//								"payment.paymentAccounts.invalid");
//						break;
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
