package com.jyh.exceptions;

@SuppressWarnings("serial")
public class SQLStatementIsErrorException extends Exception {

	public SQLStatementIsErrorException(String message){
		super(message);
	}
}
