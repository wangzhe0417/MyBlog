package com.jyh.action;


import org.apache.struts2.ServletActionContext;

import com.jyh.domain.User;
import com.jyh.utils.ErrorMessageUtil;
import com.jyh.utils.JsonConvertUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MyBaseAction extends ActionSupport {
	
	private String message;
	private String state;
	protected ErrorMessageUtil errorMessage = new ErrorMessageUtil(); //保存错误信息
	protected User baseUser = (User)ServletActionContext.getContext().getSession().get("user");
	protected User visitedUser = (User)ServletActionContext.getContext().getSession().get("visitedUser");
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	protected boolean haveError(String error){
		if(!errorMessage.isEmpty()){
			try {
				setState("1");
				setMessage(JsonConvertUtil.returnJson(errorMessage.getErrors()));;
			} catch (Exception e) {
				setState("2");
				setMessage(error);
			}
			return true;
		}
		return false;
	}
	
}
