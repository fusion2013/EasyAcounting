package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.ItemGroup;

public interface ItemGroupRepository extends JpaRepository<ItemGroup, Integer> {

	List<ItemGroup> findByFileAndDeleted(File file, boolean b);

	ItemGroup findByDescriptionAndFileAndDeleted(String description, File file,
			boolean b);

}
