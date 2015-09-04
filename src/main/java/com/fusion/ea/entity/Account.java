package com.fusion.ea.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@Column(name="profit_loss")
	private boolean profitLoss;
	private boolean debtor;
	@Column(name="cash_bank")
	private boolean cashBank;
	@Column(name="opening_balance")
	private double openingBalance;
	@Transient
	private String openingBalanceString="0";
	private boolean credit;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="group_id")
	private AccountGroup accountGroup;
	@ManyToOne
	@JoinColumn(name="file_id")
	private File file;
	
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
	public boolean isProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(boolean profitLoss) {
		this.profitLoss = profitLoss;
	}
	public boolean isDebtor() {
		return debtor;
	}
	public void setDebtor(boolean debtor) {
		this.debtor = debtor;
	}
	public boolean isCashBank() {
		return cashBank;
	}
	public void setCashBank(boolean cashBank) {
		this.cashBank = cashBank;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	public AccountGroup getAccountGroup() {
		return accountGroup;
	}
	public void setAccountGroup(AccountGroup accountGroup) {
		this.accountGroup = accountGroup;
	}
	
	public String getOpeningBalanceString() {
		return openingBalanceString;
	}
	public void setOpeningBalanceString(String openingBalanceString) {
		this.openingBalanceString = openingBalanceString;
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
//	public String getProfitLossString() {
//		return profitLossString;
//	}
//	public void setProfitLossString(String profitLossString) {
//		this.profitLossString = profitLossString;
//	}
//	public String getDebtorString() {
//		return debtorString;
//	}
//	public void setDebtorString(String debtorString) {
//		this.debtorString = debtorString;
//	}
//	public String getCashBankString() {
//		return cashBankString;
//	}
//	public void setCashBankString(String cashBankString) {
//		this.cashBankString = cashBankString;
//	}
//	public String getCreditString() {
//		return creditString;
//	}
//	public void setCreditString(String creditString) {
//		this.creditString = creditString;
//	}
	
}
