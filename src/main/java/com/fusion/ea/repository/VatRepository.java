package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.Vat;

public interface VatRepository extends JpaRepository<Vat, Integer> {

	public List<Vat> findByFile(File file);
	
	public List<Vat> findByFileAndDeleted(File file, boolean deleted);

	public Vat findByNameAndFileAndDeleted(String name, File file, boolean b);

	public List<Vat> findByFileAndForSaleAndDeleted(File file, boolean b,
			boolean c);
	
}
