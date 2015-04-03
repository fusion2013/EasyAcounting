package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.SaleTax;
import com.fusion.ea.service.SaleTaxService;

public class SaleTaxValidator implements Validator {

	private SaleTaxService saleTaxService;

	public SaleTaxValidator(SaleTaxService saleTaxService) {
		this.saleTaxService = saleTaxService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SaleTax.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			SaleTax saleTax = (SaleTax) obj;
			if (saleTax.getName() == null
					|| saleTax.getName().trim().equals("")) {
				System.out.println("1");
				errors.rejectValue("name", "saletax.name.empty");
			} else {
				SaleTax oldSaleTax = saleTaxService.findByName(saleTax
						.getName());
				if (oldSaleTax != null) {
					if (saleTax.getId() == null) {
						System.out.println("2");
						errors.rejectValue("name", "saletax.name.duplicate");
					} else {
						if (!saleTax.getId().equals(oldSaleTax.getId())) {
							System.out.println("3");
							errors.rejectValue("name", "saletax.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
