package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.Item;
import com.fusion.ea.entity.File;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	public List<Item> findByFile(File file);
	
	public List<Item> findByFileAndDeleted(File file, boolean deleted);

	public Item findByNameAndFileAndDeleted(String name, File file, boolean b);
	
}
