package com.fusion.ea.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.entity.Vat;
import com.fusion.ea.repository.VatRepository;

@Service
public class VatService {

	@Autowired
	private VatRepository vatRepository;
	
	@Autowired
	private SessionService sessionService;
	
	public Vat findOne(int id) throws Exception {
		return vatRepository.findOne(id);
	}
	
	public List<Vat> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return vatRepository.findByFileAndDeleted(file,false);
	}
	
	public List<Vat> findAllForSale() throws Exception {
		File file = sessionService.getSelectedFile();
		return vatRepository.findByFileAndForSaleAndDeleted(file,true,false);
	}
	
	public List<Vat> findAllForPurchase() throws Exception {
		File file = sessionService.getSelectedFile();
		return vatRepository.findByFileAndForSaleAndDeleted(file,false,false);
	}

	public Vat save(Vat vat) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		vat.setCreatedAt(new Date());
		vat.setCreatedBy(user);
		vat.setFile(sessionService.getSelectedFile());
		return vatRepository.save(vat);
	}
	
	public Vat update(Vat vat) throws Exception {
		Vat oldVat = vatRepository.findOne(vat.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		oldVat.setName(vat.getName());
		oldVat.setForSale(vat.isForSale());
		oldVat.setPerc(vat.getPerc());
		oldVat.setPostAccount(vat.getPostAccount());
		oldVat.setModifiedAt(new Date());
		oldVat.setModifiedBy(user);
		return vatRepository.save(oldVat);
	}
	
	public void delete(Vat vat) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		vat.setDeleted(true);
		vat.setDeletedAt(new Date());
		vat.setDeletedBy(user);
		vatRepository.save(vat);
	}

	public Vat findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		File file = sessionService.getSelectedFile();
		return vatRepository.findByNameAndFileAndDeleted(name, file, false);
	}
	
}
