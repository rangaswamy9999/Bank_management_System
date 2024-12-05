package com.RMe_Bank.exceptions;

public class BankUserException extends RuntimeException
{
	private String msg;
	public BankUserException(String msg) {
		this.msg=msg;
	}
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}
	
}
