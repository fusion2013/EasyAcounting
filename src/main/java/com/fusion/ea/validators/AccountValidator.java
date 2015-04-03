package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Account;
import com.fusion.ea.service.AccountService;

public class AccountValidator implements Validator {

	private AccountService accountService;

	public AccountValidator(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Account account = (Account) obj;
			if (account.getName() == null
					|| account.getName().trim().equals("")) {
				errors.rejectValue("name", "account.name.empty");
			} else {
				Account oldAccount = accountService.findByName(account
						.getName());
				if (oldAccount != null) {
					if (account.getId() == null) {
						errors.rejectValue("name", "account.name.duplicate");
					} else {
						if (!account.getId().equals(oldAccount.getId())) {
							errors.rejectValue("name", "account.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
