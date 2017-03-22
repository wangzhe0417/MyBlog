package com.jyh.utils;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class OptionLogAspect {
	
	
	public void beforeMethod(JoinPoint joinPoint){
	}
	
	public void afterMethod(JoinPoint joinPoint,Object val){
	
		Logger logger = Logger.getLogger(OptionLogAspect.class);
		MDC.put("id", UUID.randomUUID().toString());
		MDC.put("userId", "111");
		MDC.put("userName", "dddd");
		MDC.put("methodName", joinPoint.getSignature().getName());
		MDC.put("className", joinPoint.getTarget().getClass().toString());
		logger.info(val);
	}
	
	public void finallyMethod(){
		
	}
	
	public void exceptionMethod(JoinPoint joinPoint,Throwable ex){
		System.out.println(ex.getMessage());
	}

	public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
		joinPoint.proceed();//调用目标方法
	}
}
