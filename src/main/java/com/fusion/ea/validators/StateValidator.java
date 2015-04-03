package com.fusion.ea.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fusion.ea.entity.State;
import com.fusion.ea.service.StateService;

public class StateValidator implements Validator {

	private StateService stateService;

	public StateValidator(StateService stateService) {
		this.stateService = stateService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return State.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		try {
			State state = (State) obj;
			if (state.getName() == null
					|| state.getName().trim().equals("")) {
				errors.rejectValue("name",
						"state.name.empty");
			} else {
				State oldState = stateService.findByDescription(state
						.getName());
				if (oldState != null) {
					if (state.getId() == null) {
						errors.rejectValue("name",
								"state.name.duplicate");
					} else {
						if (!state.getId().equals(oldState.getId())) {
							errors.rejectValue("name",
									"state.name.duplicate");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
