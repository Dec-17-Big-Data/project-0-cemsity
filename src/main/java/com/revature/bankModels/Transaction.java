package com.revature.bankModels;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2177378413204015167L;
	private Integer transactionId;
	private Integer account1Id;
	private Integer account2Id;
	private Integer amount;
	private String  type;
	private Date timeStamp;
	
	
	public Transaction() {
		super();
	}


	public Integer getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}


	public Integer getAccount1Id() {
		return account1Id;
	}


	public void setAccount1Id(Integer account1Id) {
		this.account1Id = account1Id;
	}


	public Integer getAccount2Id() {
		return account2Id;
	}


	public void setAccount2Id(Integer account2Id) {
		this.account2Id = account2Id;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account1Id == null) ? 0 : account1Id.hashCode());
		result = prime * result + ((account2Id == null) ? 0 : account2Id.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (account1Id == null) {
			if (other.account1Id != null)
				return false;
		} else if (!account1Id.equals(other.account1Id))
			return false;
		if (account2Id == null) {
			if (other.account2Id != null)
				return false;
		} else if (!account2Id.equals(other.account2Id))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", account1Id=" + account1Id + ", account2Id="
				+ account2Id + ", amount=" + amount + ", type=" + type + ", timeStamp=" + timeStamp + "]";
	}
	
	
	
}
