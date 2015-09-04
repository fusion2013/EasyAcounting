package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Vat;
import com.fusion.ea.service.VatService;

public class VatValidator implements Validator {

	private VatService vatService;

	public VatValidator(VatService vatService) {
		this.vatService = vatService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Vat.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Vat vat = (Vat) obj;
			if (vat.getName() == null
					|| vat.getName().trim().equals("")) {
				errors.rejectValue("name", "vat.name.empty");
			} else {
				Vat oldVat = vatService.findByName(vat
						.getName());
				if (oldVat != null) {
					if (vat.getId() == null) {
						errors.rejectValue("name", "vat.name.duplicate");
					} else {
						if (!vat.getId().equals(oldVat.getId())) {
							errors.rejectValue("name", "vat.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
