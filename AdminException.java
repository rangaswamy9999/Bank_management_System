package com.RMe_Bank.Exception;

public class AdminException extends RuntimeException
{
	private String eMsg;

	public AdminException(String eMsg) 
	{
		this.eMsg = eMsg;
	}

	public String geteMsg() {
		return eMsg;
	}
}
