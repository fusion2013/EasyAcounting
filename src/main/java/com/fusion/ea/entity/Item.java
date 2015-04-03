package com.fusion.ea.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private boolean goods=true;
	@Column(name="purchase_rate")
	private double purchaseRate;
	@Column(name="sale_rate")
	private double saleRate;
	@Column(name="opening_qty")
	private double openingQty;
	@Column(name="opening_value")
	private double openingValue;
	
	@Transient
	private String purchaseRateString="0";
	@Transient
	private String saleRateString="0";
	@Transient
	private String openingQtyString="0";
	@Transient
	private String openingValueString="0";

	@ManyToOne
	@JoinColumn(name="purchase_vat_id")
	private Vat purchaseVat;
	@ManyToOne
	@JoinColumn(name="sale_vat_id")
	private Vat saleVat;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private ItemGroup itemGroup;
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isGoods() {
		return goods;
	}
	public void setGoods(boolean goods) {
		this.goods = goods;
	}
	
	public String getPurchaseRateString() {
		return purchaseRateString;
	}
	public void setPurchaseRateString(String purchaseRateString) {
		this.purchaseRateString = purchaseRateString;
	}
	public String getSaleRateString() {
		return saleRateString;
	}
	public void setSaleRateString(String saleRateString) {
		this.saleRateString = saleRateString;
	}
	public String getOpeningQtyString() {
		return openingQtyString;
	}
	public void setOpeningQtyString(String openingQtyString) {
		this.openingQtyString = openingQtyString;
	}
	public String getOpeningValueString() {
		return openingValueString;
	}
	public void setOpeningValueString(String openingValueString) {
		this.openingValueString = openingValueString;
	}
	public double getPurchaseRate() {
		return purchaseRate;
	}
	public void setPurchaseRate(double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}
	public double getSaleRate() {
		return saleRate;
	}
	public void setSaleRate(double saleRate) {
		this.saleRate = saleRate;
	}
	public double getOpeningQty() {
		return openingQty;
	}
	public void setOpeningQty(double openingQty) {
		this.openingQty = openingQty;
	}
	public double getOpeningValue() {
		return openingValue;
	}
	public void setOpeningValue(double openingValue) {
		this.openingValue = openingValue;
	}
	
	public Vat getPurchaseVat() {
		return purchaseVat;
	}
	public void setPurchaseVat(Vat purchaseVat) {
		this.purchaseVat = purchaseVat;
	}
	public Vat getSaleVat() {
		return saleVat;
	}
	public void setSaleVat(Vat saleVat) {
		this.saleVat = saleVat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public ItemGroup getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}
