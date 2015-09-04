package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.State;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public State findById(Integer id) throws Exception {
		return stateRepository.findOne(id);
	}
	
	public List<State> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return stateRepository.findByFileAndDeleted(file, false);
	}
	
	public State save(State state) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		state.setCreatedAt(new Date());
		state.setCreatedBy(user);
		state.setFile(sessionService.getSelectedFile());
		return stateRepository.save(state);
	}
	
	public State update(State state) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		state.setModifiedAt(new Date());
		state.setModifiedBy(user);
		return stateRepository.save(state);
	}
	
	public void delete(State state) throws Exception {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
		state.setDeletedAt(new Date());
		state.setDeletedBy(user);
		state.setDeleted(true);
		stateRepository.save(state);
	}

	public State findByDescription(String description) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return stateRepository.findByNameAndFileAndDeleted(description, file, false);
	}
	
}
