package com.fusion.ea.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class File implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String companyName;
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	@OneToMany(mappedBy="file")
	private List<Account> accounts;
	
	@OneToMany(mappedBy="file")
	private List<AccountGroup> accountGroups;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public List<AccountGroup> getAccountGroups() {
		return accountGroups;
	}
	public void setAccountGroups(List<AccountGroup> accountGroups) {
		this.accountGroups = accountGroups;
	}

}
