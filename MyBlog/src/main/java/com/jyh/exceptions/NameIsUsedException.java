package com.jyh.exceptions;

@SuppressWarnings("serial")
public class NameIsUsedException extends UserException{

	public NameIsUsedException(String message){
		super(message);
	}
}
