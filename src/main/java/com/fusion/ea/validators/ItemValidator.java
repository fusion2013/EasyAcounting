package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.Item;
import com.fusion.ea.service.ItemService;

public class ItemValidator implements Validator {

	private ItemService itemService;

	public ItemValidator(ItemService itemService) {
		this.itemService = itemService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Item.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Item item = (Item) obj;
			if (item.getName() == null || item.getName().trim().equals("")) {
				errors.rejectValue("name", "item.name.empty");
			} else {
				Item oldItem = itemService.findByName(item.getName());
				if (oldItem != null) {
					if (item.getId() == null) {
						errors.rejectValue("name", "item.name.duplicate");
					} else {
						if (!item.getId().equals(oldItem.getId())) {
							errors.rejectValue("name", "item.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
