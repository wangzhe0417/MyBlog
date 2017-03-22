package com.jyh.action;

import java.io.IOException;

import com.jyh.domain.Dynamic;
import com.jyh.service.DynamicService;

@SuppressWarnings("serial")
public class DynamicAction extends MyBaseAction {

	private DynamicService dynamicService;
	private String dynamicType;

	public DynamicService getDynamicService() {
		return dynamicService;
	}

	public void setDynamicService(DynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}

	public String getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}

	/**
	 * 获取我的动态/我关注的动态
	 * @return
	 */
	public String findMyDynamic(){
		if(visitedUser != null && !visitedUser.getUserId().isEmpty()){
			try {
				switch (dynamicType) {
				case "0":
					setMessage(dynamicService.findMyDynamic(visitedUser.getUserId()));
					break;
				case "1":
					setMessage(dynamicService.findMyAttentionDynamic(visitedUser.getUserId()));
				default:
					break;
				}
				setState("0");
			} catch (IOException e) {
				setState("2");
			}
		}else
			setState("2");
		return "personal";
	};
	
}
