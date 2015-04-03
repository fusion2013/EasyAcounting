package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.ItemGroup;
import com.fusion.ea.service.ItemGroupService;

public class ItemGroupValidator implements Validator {

	private ItemGroupService itemGroupService;

	public ItemGroupValidator(ItemGroupService itemGroupService) {
		this.itemGroupService = itemGroupService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ItemGroup.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			ItemGroup group = (ItemGroup) obj;
			if (group.getDescription() == null
					|| group.getDescription().trim().equals("")) {
				errors.rejectValue("description",
						"itemgroup.description.empty");
			} else {
				ItemGroup oldGroup = itemGroupService.findByDescription(group
						.getDescription());
				if (oldGroup != null) {
					if (group.getId() == null) {
						errors.rejectValue("description",
								"itemgroup.description.duplicate");
					} else {
						if (!group.getId().equals(oldGroup.getId())) {
							errors.rejectValue("description",
									"itemgroup.description.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
