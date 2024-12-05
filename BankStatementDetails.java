package com.RMe_Bank.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class BankStatementDetails 
{
	private int transaction_id;
	private double transaction_amount;
	private LocalDate transaction_date;
	private LocalTime transaction_time;
	private double balance;
	private String transaction_type;
	private int user_id;
	public BankStatementDetails(int transaction_id, double transaction_amount, LocalDate transaction_date,
			LocalTime transaction_time, double balance, String transaction_type, int user_id) {
		//super();
		this.transaction_id = transaction_id;
		this.transaction_amount = transaction_amount;
		this.transaction_date = transaction_date;
		this.transaction_time = transaction_time;
		this.balance = balance;
		this.transaction_type = transaction_type;
		this.user_id = user_id;
	}
	public BankStatementDetails() {
		//super();
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public double getTransaction_amount() {
		return transaction_amount;
	}
	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public LocalDate getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}
	public LocalTime getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(LocalTime transaction_time) {
		this.transaction_time = transaction_time;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "BankStatementDetails [transaction_id=" + transaction_id + ", transaction_amount=" + transaction_amount
				+ ", transaction_date=" + transaction_date + ", transaction_time=" + transaction_time + ", balance="
				+ balance + ", transaction_type=" + transaction_type + ", user_id=" + user_id + "]";
	}
		
}
