package com.fusion.ea.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="user_name")
	private String userName;
	private String password;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	private String position;
	@Column(name="birth_date")
	private Date birthDate;
	private String gender;
	
	@Column(name="email_id")
	private String emailId;
	@Column(name="contact_no")
	private String contactNo;
	private String zipcode;
	private String city;
	private String state;
	private String address;
	
	private boolean admin;
	@ManyToMany
	@JoinTable
	private List<Role> roles;
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	
	@Transient
	private String day;
	@Transient
	private String month;
	@Transient
	private String year;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
