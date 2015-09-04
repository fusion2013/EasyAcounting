package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.AccountGroup;
import com.fusion.ea.entity.File;

public interface AccountGroupRepository extends JpaRepository<AccountGroup, Integer> {

	public List<AccountGroup> findByFileAndDeleted(File file, boolean deleted);

	public AccountGroup findByDescriptionAndFileAndDeleted(String description,
			File file, boolean b);
	
}
