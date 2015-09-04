package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.SaleTax;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.SaleTaxRepository;

@Service
public class SaleTaxService {

	@Autowired
	private SaleTaxRepository saleTaxRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public SaleTax findOne(int id) throws Exception {
		return saleTaxRepository.findOne(id);
	}
	
	public List<SaleTax> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return saleTaxRepository.findByFileAndDeleted(file,false);
	}

	public SaleTax save(SaleTax saleTax) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		saleTax.setCreatedAt(new Date());
		saleTax.setCreatedBy(user);
		saleTax.setFile(sessionService.getSelectedFile());
		return saleTaxRepository.save(saleTax);
	}
	
	public SaleTax update(SaleTax saleTax) throws Exception {
		SaleTax oldSaleTax = saleTaxRepository.findOne(saleTax.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		oldSaleTax.setName(saleTax.getName());
		oldSaleTax.setForSale(saleTax.isForSale());
		oldSaleTax.setPerc(saleTax.getPerc());
		oldSaleTax.setPostAccount(saleTax.getPostAccount());
		oldSaleTax.setModifiedAt(new Date());
		oldSaleTax.setModifiedBy(user);
		return saleTaxRepository.save(oldSaleTax);
	}
	
	public void delete(SaleTax saleTax) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		saleTax.setDeleted(true);
		saleTax.setDeletedAt(new Date());
		saleTax.setDeletedBy(user);
		saleTaxRepository.save(saleTax);
	}

	public SaleTax findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return saleTaxRepository.findByNameAndFileAndDeleted(name, file, false);
	}
	
}
