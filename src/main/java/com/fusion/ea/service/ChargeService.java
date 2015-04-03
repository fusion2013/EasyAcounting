package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.entity.Charge;
import com.fusion.ea.repository.ChargeRepository;

@Service
public class ChargeService {

	@Autowired
	private ChargeRepository chargeRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public Charge findOne(int id) throws Exception {
		return chargeRepository.findOne(id);
	}
	
	public List<Charge> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return chargeRepository.findByFileAndDeleted(file,false);
	}

	public Charge save(Charge charge) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		charge.setCreatedAt(new Date());
		charge.setCreatedBy(user);
		charge.setFile(sessionService.getSelectedFile());
		return chargeRepository.save(charge);
	}
	
	public Charge update(Charge charge) throws Exception {
		Charge oldCharge = chargeRepository.findOne(charge.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		oldCharge.setName(charge.getName());
		oldCharge.setToAdd(charge.isToAdd());
		oldCharge.setPerc(charge.getPerc());
		oldCharge.setPostAccount(charge.getPostAccount());
		oldCharge.setModifiedAt(new Date());
		oldCharge.setModifiedBy(user);
		return chargeRepository.save(oldCharge);
	}
	
	public void delete(Charge charge) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		charge.setDeleted(true);
		charge.setDeletedAt(new Date());
		charge.setDeletedBy(user);
		chargeRepository.save(charge);
	}

	public Charge findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return chargeRepository.findByNameAndFileAndDeleted(name, file, false);
	}
	
}
