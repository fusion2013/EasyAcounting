package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.File;
import com.fusion.ea.service.FileService;

public class FileValidator implements Validator {

	private FileService fileServise;

	public FileValidator(FileService fileServise) {
		this.fileServise = fileServise;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return File.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		try {
			// TODO Auto-generated method stub
			File file = (File) obj;
			if (file.getName() == null || file.getName().trim().equals("")) {
				errors.rejectValue("name", "file.name.empty");
			} else {
				File oldFile = fileServise.findByNameAndCompany(file.getName());
				if (oldFile != null) {
					if (file.getId() == null) {
						errors.rejectValue("name", "file.name.duplicate");
					} else {
						if (!file.getId().equals(oldFile.getId())) {
							errors.rejectValue("name", "file.name.duplicate");
						}
					}
				}
			}
			if (file.getCompanyName() == null
					|| file.getCompanyName().trim().equals("")) {
				errors.rejectValue("companyName", "file.cname.empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
