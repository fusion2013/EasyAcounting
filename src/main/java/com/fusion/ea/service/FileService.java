package com.fusion.ea.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Company;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	HttpSession session;
	
	public File findById(Integer id) throws Exception {
		return fileRepository.findOne(id);
	}
	
	public File save(File file) throws Exception {
		return fileRepository.save(file);
	}
	
	public List<File> findByCompany(Company company) throws Exception {
		return fileRepository.findByCompany(company);
	}
	
	public void delete(File file) throws Exception {
		fileRepository.delete(file);
	}
	
	public File findByNameAndCompany(String name) throws Exception {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getDetails();
		return fileRepository.findByNameAndCompany(name,user.getCompany());
	}
	
}
