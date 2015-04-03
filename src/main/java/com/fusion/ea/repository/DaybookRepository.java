package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Daybook;
import com.fusion.ea.entity.File;

public interface DaybookRepository extends JpaRepository<Daybook, Integer> {

	public List<Daybook> findByFileAndDeleted(File file, boolean deleted);

	public Daybook findByNameAndFileAndDeleted(String name,
			File file, boolean b);
	
}
