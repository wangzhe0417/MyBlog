package com.jyh.exceptions;

@SuppressWarnings("serial")
public class UserIsNotFound extends UserException {

	public UserIsNotFound(String message){
		super(message);
	}
}
