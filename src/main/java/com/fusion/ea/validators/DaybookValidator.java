package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Daybook;
import com.fusion.ea.service.DaybookService;

public class DaybookValidator implements Validator {

	private DaybookService daybookService;

	public DaybookValidator(DaybookService daybookService) {
		this.daybookService = daybookService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Daybook.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Daybook daybook = (Daybook) obj;
			if (daybook.getName() == null
					|| daybook.getName().trim().equals("")) {
				errors.rejectValue("name",
						"daybook.name.empty");
			} else {
				Daybook oldDaybook = daybookService.findByDescription(daybook
						.getName());
				if (oldDaybook != null) {
					if (daybook.getId() == null) {
						errors.rejectValue("name",
								"daybook.name.duplicate");
					} else {
						if (!daybook.getId().equals(oldDaybook.getId())) {
							errors.rejectValue("name",
									"daybook.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
