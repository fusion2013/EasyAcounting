package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Charge;
import com.fusion.ea.entity.File;

public interface ChargeRepository extends JpaRepository<Charge, Integer> {
	
	public List<Charge> findByFile(File file);
	
	public List<Charge> findByFileAndDeleted(File file, boolean deleted);

	public Charge findByNameAndFileAndDeleted(String name, File file, boolean b);

}
