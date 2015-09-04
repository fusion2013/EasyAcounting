package com.fusion.ea.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.Company;
import com.fusion.ea.entity.File;
import com.fusion.ea.entity.Role;
import com.fusion.ea.entity.User;
import com.fusion.ea.repository.CompanyRepository;
import com.fusion.ea.repository.FileRepository;
import com.fusion.ea.repository.RoleRepository;
import com.fusion.ea.repository.UserRepository;

@Transactional
@Service
public class InitDbService {

	@Autowired
	private CompanyRepository compRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private FileRepository fileRepository;

	@PostConstruct
	public void init() {
		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//			Role userR = new Role();
//			userR.setName("ROLE_USER");
//			Role adminR = new Role();
//			adminR.setName("ROLE_ADMIN");
//
//			roleRepository.save(userR);
//			roleRepository.save(adminR);
//
//			Company comp = new Company();
//			comp.setActive(true);
//			comp.setName("N.J.Account Service");
//			comp.setContactNo("07927454133");
//			comp.setAddress("G1, Himadri Complex, Opp. Sales India, IncomTax");
//			comp.setCity("Ahmedabad");
//			comp.setState("Gujarat");
//			comp.setZipcode("380006");
//			compRepository.save(comp);
//			
//			Company comp1 = new Company();
//			comp1.setActive(true);
//			comp1.setAddress("A/23, Shubhlaxmi Towers, Naranpura, Abad-13.");
//			comp1.setName("Fusion solutions");
//			comp1.setContactNo("07927454133");
//			comp1.setCity("Ahmedabad");
//			comp1.setState("Gujarat");
//			comp1.setZipcode("380013");
//			compRepository.save(comp1);
//
//			User adminUser = new User();
//			adminUser.setAdmin(true);
//			adminUser.setCompany(comp);
//			adminUser.setFirstName("Abhishek");
//			adminUser.setLastName("Upadhyay");
//			adminUser.setUserName("abhiit61");
//			adminUser.setPassword(("abhishek"));
//			adminUser.setAddress("A/23, Shubhlaxmi Towers");
//			adminUser.setBirthDate(sdf.parse("26-01-1991"));
//			adminUser.setCity("Ahmedabad");
//			adminUser.setContactNo("9724465112");
//			adminUser.setEmailId("abhiit61@gmail.com");
//			adminUser.setGender("Male");
//			adminUser.setPosition("Boss");
//			adminUser.setState("Gujarat");
//			adminUser.setZipcode("380013");
//			List<Role> roles = new ArrayList<Role>();
//			roles.add(userR);
//			roles.add(adminR);
//			adminUser.setRoles(roles);
//			userRepository.save(adminUser);
//
//			User normUser = new User();
//			normUser.setAdmin(false);
//			normUser.setCompany(comp);
//			normUser.setFirstName("Nirav");
//			normUser.setLastName("Upadhyay");
//			normUser.setUserName("niravit57");
//			normUser.setPassword(("niravnirav"));
//			normUser.setAddress("A/23, Shubhlaxmi Towers");
//			normUser.setBirthDate(sdf.parse("21-05-1989"));
//			normUser.setCity("Ahmedabad");
//			normUser.setContactNo("9601447799");
//			normUser.setEmailId("niravit57@gmail.com");
//			normUser.setGender("Male");
//			normUser.setPosition("Boss");
//			normUser.setState("Gujarat");
//			normUser.setZipcode("380013");
//			roles.remove(1);
//			normUser.setRoles(roles);
//			userRepository.save(normUser);
//			
//			File f1 = new File();
//			f1.setCompany(comp);
//			f1.setCompanyName("Jaiswal Group");
//			f1.setName("Jaiswal Canteen A");
//			
//			File f2 = new File();
//			f2.setCompany(comp);
//			f2.setCompanyName("Jaiswal Group");
//			f2.setName("Jaiswal Canteen V");
//			
//			File f3 = new File();
//			f3.setCompany(comp1);
//			f3.setCompanyName("Minetree");
//			f3.setName("Ankit K");
//			
//			fileRepository.save(f1);
//			fileRepository.save(f2);
//			fileRepository.save(f3);
//			
//			System.out.println("SAVED...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
