package com.fusion.ea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Company;
import com.fusion.ea.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	public void save(Company company) throws Exception {
		companyRepository.save(company);
	}

}
