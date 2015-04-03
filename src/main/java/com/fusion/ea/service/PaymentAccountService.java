package com.fusion.ea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Payment;
import com.fusion.ea.entity.PaymentAccount;
import com.fusion.ea.repository.PaymentAccountRepository;

@Service
public class PaymentAccountService {

	@Autowired
	private PaymentAccountRepository paymentAccRepository;
	
	public PaymentAccount save(PaymentAccount paymentAcc) throws Exception {
		return paymentAccRepository.save(paymentAcc);
	}
	
}
