package com.jyh.action;

import java.io.IOException;

import com.jyh.domain.PersonalClassification;
import com.jyh.service.PersonalClassificationService;

@SuppressWarnings("serial")
public class PersonalClassificationAction extends MyBaseAction {

	private PersonalClassificationService personalClassificationService;
	private PersonalClassification pClassification;
	
	public PersonalClassificationService getPersonalClassificationService() {
		return personalClassificationService;
	}
	public void setPersonalClassificationService(
			PersonalClassificationService personalClassificationService) {
		this.personalClassificationService = personalClassificationService;
	}
	public PersonalClassification getpClassification() {
		return pClassification;
	}
	public void setpClassification(PersonalClassification pClassification) {
		this.pClassification = pClassification;
	}
	
	/**
	 * 根据用户id获取用户的分类
	 * @return
	 */
	public String findMyClass(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "myclass";
		}
		try {
			setMessage(personalClassificationService.findUserClass(baseUser.getUserId()));
			setState("0");
		} catch (IOException e) {
			setState("2");
			setMessage("获取失败!");
		};
		return "myclass";
	}
	
	/**
	 * 删除分类
	 */
	public String deleteMyClass(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "myclass";
		}
		if(pClassification == null || pClassification.getClassificationId().isEmpty()){
			setState("2");
			setMessage("删除失败!");
			return "myclass";
		}
		personalClassificationService.deleteEntityById(pClassification.getClassificationId());
		setState("0");
		return "myclass";
	}
	
	/**
	 * 保存分类
	 */
	public String saveMyClass(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "myclass";
		}
		if(pClassification == null){
			setState("2");
			setMessage("保存失败!");
			return "myclass";
		}else if(pClassification.getClassificationId() == null || pClassification.getClassificationId().isEmpty()){
			personalClassificationService.saveEntity(pClassification);
			setState("0");
		}else{
			personalClassificationService.saveUserClass(pClassification);
			setState("0");
		}
		return "myclass";
	}
}
