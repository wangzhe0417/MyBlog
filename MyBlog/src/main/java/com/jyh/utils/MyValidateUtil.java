package com.jyh.utils;

import java.util.Random;
/**
 * 验证码生成工具类
 * @author OverrideRe
 *
 */
public class MyValidateUtil {

	public static String getSimpleCode() {
		String[] verificationCodeArrary = { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z" };
		String verificationCode = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			verificationCode += verificationCodeArrary[random
					.nextInt(verificationCodeArrary.length)];
		}
		return verificationCode;
	}
	
}
