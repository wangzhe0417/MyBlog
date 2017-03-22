package com.jyh.action;

import java.io.IOException;

import com.jyh.domain.Attention;
import com.jyh.domain.User;
import com.jyh.service.AttentionService;

@SuppressWarnings("serial")
public class AttentionAction extends MyBaseAction {

	private AttentionService attentionService;
	private Attention attention;
	private String attentionType;
	public AttentionService getAttentionService() {
		return attentionService;
	}
	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}
	public Attention getAttention() {
		return attention;
	}
	public void setAttention(Attention attention) {
		this.attention = attention;
	}
	public String getAttentionType() {
		return attentionType;
	}
	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}
	/**
	 * 关注
	 * @return
	 */
	public String attentionUser(){
		if(visitedUser != null && !visitedUser.getUserId().isEmpty()){
			attentionService.saveEntity(new Attention(visitedUser,baseUser));
			setState("0");
			setMessage("关注成功!");
		}else{
			setState("2");
			setMessage("关注失败!");
		}
		return "attention";
	}
	
	/**
	 * 取消关注
	 * @return
	 */
	public String cancelAttention(){
		if(attention != null && !attention.getAttentionId().isEmpty()){
			attentionService.deleteEntityById(attention.getAttentionId());
			setState("0");
			setMessage("取消成功!");
		}else{
			setState("0");
			setMessage("取消关注失败!");
		}
		return "attention";
	}
	
	/**
	 * 获取我关注的人/我关注的人/互相关注的人
	 */
	public String getMyAttention(){
		if(visitedUser != null && !visitedUser.getUserId().isEmpty()){
			try {
				switch(attentionType)
				{
				case "0"://我关注的
					setMessage(attentionService.findMyAttention(visitedUser.getUserId()));
					break;
				case "1"://关注我的
					setMessage(attentionService.findFollowMe(visitedUser.getUserId()));
					break;
				case "2"://互相关注的
					setMessage(attentionService.findMutualConcern(visitedUser.getUserId()));
					break;
				}
				
				setState("0");
			} catch (IOException e) {
				setState("0");
				setMessage("出错了!");
			}
		}else{
			setState("0");
			setMessage("出错了!");
		}
		return "attention";
	};
	
}
