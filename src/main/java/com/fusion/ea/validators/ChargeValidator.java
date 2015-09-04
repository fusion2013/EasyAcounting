package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Charge;
import com.fusion.ea.service.ChargeService;

public class ChargeValidator implements Validator {

	private ChargeService chargeService;

	public ChargeValidator(ChargeService chargeService) {
		this.chargeService = chargeService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Charge.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Charge charge = (Charge) obj;
			if (charge.getName() == null
					|| charge.getName().trim().equals("")) {
				errors.rejectValue("name", "charge.name.empty");
			} else {
				Charge oldCharge = chargeService.findByName(charge
						.getName());
				if (oldCharge != null) {
					if (charge.getId() == null) {
						errors.rejectValue("name", "charge.name.duplicate");
					} else {
						if (!charge.getId().equals(oldCharge.getId())) {
							errors.rejectValue("name", "charge.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
