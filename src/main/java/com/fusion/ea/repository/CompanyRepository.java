package com.fusion.ea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Company;


public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
}
