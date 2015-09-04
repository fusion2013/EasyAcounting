package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Company;
import com.fusion.ea.entity.File;


public interface FileRepository extends JpaRepository<File, Integer> {

	public List<File> findByCompany(Company company);

	public File findByNameAndCompany(String name, Company company);
	
}
