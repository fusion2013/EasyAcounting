package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.AccountGroup;
import com.fusion.ea.service.AccountGroupService;

public class AccountGroupValidator implements Validator {

	private AccountGroupService accGroupService;

	public AccountGroupValidator(AccountGroupService accGroupService) {
		this.accGroupService = accGroupService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return AccountGroup.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			AccountGroup group = (AccountGroup) obj;
			if (group.getDescription() == null
					|| group.getDescription().trim().equals("")) {
				errors.rejectValue("description",
						"accountgroup.description.empty");
			} else {
				AccountGroup oldGroup = accGroupService.findByDescription(group
						.getDescription());
				if (oldGroup != null) {
					if (group.getId() == null) {
						errors.rejectValue("description",
								"accountgroup.description.duplicate");
					} else {
						if (!group.getId().equals(oldGroup.getId())) {
							errors.rejectValue("description",
									"accountgroup.description.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
