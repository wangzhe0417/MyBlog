package com.jyh.exceptions;

import org.springframework.mail.MailException;

@SuppressWarnings("serial")
public class MyEmailException extends MailException{

	public MyEmailException(String msg) {
		super(msg);
	}

}
