package com.fusion.ea.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="sr_no")
	private String srNo;
	@Column(name="ref_no")
	private String refNo;
	private Date date;
	private double total;
	
	@ManyToOne
	@JoinColumn(name="cash_bank_account_id")
	private Account cashBankAccount;
	
	@ManyToOne
	@JoinColumn(name="file_id")
	private File file;
	
	@OneToMany(mappedBy="paymentId",cascade=CascadeType.ALL)
	List<PaymentAccount> paymentAccounts = new ArrayList<PaymentAccount>();
	
	@Column(name="deleted")
	private boolean deleted=false;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="modified_at")
	private Date modifiedAt;
	@Column(name="deleted_at")
	private Date deletedAt;
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name="modified_by")
	private User modifiedBy;
	@ManyToOne
	@JoinColumn(name="deleted_by")
	private User deletedBy;
	
	@Transient
	private String dateString;
	@Transient
	private String cashBankString;
	
	
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Account getCashBankAccount() {
		return cashBankAccount;
	}
	public void setCashBankAccount(Account cashBankAccount) {
		this.cashBankAccount = cashBankAccount;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public User getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public User getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}
	public List<PaymentAccount> getPaymentAccounts() {
		return paymentAccounts;
	}
	public void setPaymentAccounts(List<PaymentAccount> paymentAccounts) {
		this.paymentAccounts = paymentAccounts;
	}
	public String getCashBankString() {
		return cashBankString;
	}
	public void setCashBankString(String cashBankString) {
		this.cashBankString = cashBankString;
	}
	
}
