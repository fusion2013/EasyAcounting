package com.fusion.ea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.PaymentAccount;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {

}
