package com.fusion.ea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.Payment;
import com.fusion.ea.entity.PaymentAccount;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentAccountService paymentAccService;
	
	@Autowired
	private SessionService sessionService;
	
	public Payment findOne(int id) throws Exception {
		return paymentRepository.findOne(id);
	}
	
	public List<Payment> findAll() throws Exception {
		File file = sessionService.getSelectedFile();
		return paymentRepository.findByFileAndDeleted(file,false);
	}

	public Payment save(Payment payment) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		payment.setCreatedAt(new Date());
		payment.setCreatedBy(user);
		payment.setFile(sessionService.getSelectedFile());
		return paymentRepository.save(payment);
	}
	
	public Payment update(Payment payment) throws Exception {
		Payment oldPayment = paymentRepository.findOne(payment.getId());
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
//		oldPayment.setName(payment.getName());
//		oldPayment.setGoods(payment.isGoods());
//		oldPayment.setOpeningQty(payment.getOpeningQty());
//		oldPayment.setOpeningValue(payment.getOpeningValue());
//		oldPayment.setPurchaseRate(payment.getPurchaseRate());
//		oldPayment.setPurchaseVat(payment.getPurchaseVat());
//		oldPayment.setSaleRate(payment.getSaleRate());
//		oldPayment.setSaleVat(payment.getSaleVat());
		
		oldPayment.setModifiedAt(new Date());
		oldPayment.setModifiedBy(user);
		return paymentRepository.save(oldPayment);
	}
	
	public void delete(Payment payment) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		payment.setDeleted(true);
		payment.setDeletedAt(new Date());
		payment.setDeletedBy(user);
		paymentRepository.save(payment);
	}

}
