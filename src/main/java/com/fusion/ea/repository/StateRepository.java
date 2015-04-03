package com.fusion.ea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusion.ea.entity.State;
import com.fusion.ea.entity.File;

public interface StateRepository extends JpaRepository<State, Integer> {

	public List<State> findByFileAndDeleted(File file, boolean deleted);

	public State findByNameAndFileAndDeleted(String name,
			File file, boolean b);
	
}
