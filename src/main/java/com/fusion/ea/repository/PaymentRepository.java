package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	public List<Payment> findByFileAndDeleted(File file, boolean b);

}
