package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.SaleTax;

public interface SaleTaxRepository extends JpaRepository<SaleTax, Integer> {

	public List<SaleTax> findByFile(File file);
	
	public List<SaleTax> findByFileAndDeleted(File file, boolean deleted);

	public SaleTax findByNameAndFileAndDeleted(String name, File file, boolean b);
	
}
