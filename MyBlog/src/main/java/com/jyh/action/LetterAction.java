package com.jyh.action;

import java.io.IOException;
import java.util.Calendar;

import com.jyh.domain.Letter;
import com.jyh.domain.User;
import com.jyh.service.LetterService;

public class LetterAction extends MyBaseAction {

	private LetterService letterService;
	private Letter letter;
	private User user;
	public LetterService getLetterService() {
		return letterService;
	}
	public void setLetterService(LetterService letterService) {
		this.letterService = letterService;
	}
	public Letter getLetter() {
		return letter;
	}
	public void setLetter(Letter letter) {
		this.letter = letter;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String findMySendLetters(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "letter";
		}
		try {
			setMessage(letterService.findSendLetter(baseUser.getUserId()));;
			setState("0");
		} catch (IOException e) {
			setState("2");
			setMessage("获取失败!");
		}
		return "letter";
	}
	
	public String findMyReceiveLetters(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "letter";
		}
		try {
			setMessage(letterService.findReceiveLetter(baseUser.getUserId()));;
			setState("0");
		} catch (IOException e) {
			setState("2");
			setMessage("获取失败!");
		}
		return "letter";
	}
	
	/**
	 * 发送私信
	 */
	public String sendLetter(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "letter";
		}
		if(user != null && user.getUserId() != null){
			letter.setSendDate(Calendar.getInstance().getTime());
			letter.setUserByReceiveUser(user);
			letter.setUserBySendUser(baseUser);
			letterService.saveEntity(letter);
			setState("0");
			setMessage("发送成功!");
		}else {
			setState("2");
			setMessage("发送失败!");
		}
		return "letter";
	};
	
	/**
	 * 删除私信
	 */
	public String deleteLetter(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "letter";
		}
		if(letter != null && !letter.getLetterId().isEmpty()){
			letterService.deleteEntityById(letter.getLetterId());
			setState("0");
			setMessage("删除成功!");
		}else {
			setState("2");
			setMessage("删除失败!");
		}
		return "letter";
	}
}
