package com.lingnet.util;

import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import com.lingnet.qxgl.entity.QxUsers;

/**
 * 使用盐值的SHA-256加密
 * 
 * @author Jerry
 * 
 */
public class SHA_256 {

	/**
	 * 返回SHA-256加密密码
	 * 
	 * @param rawPass
	 *            未加密密码
	 * @param userDetails
	 * @return
	 */
	public static String encodePassword(String rawPass, UserDetails userDetails) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
		ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		// 以用户名作为加密盐值
		reflectionSaltSource.setUserPropertyToUse("username");
		// 反射调用getUsername()的用户名
		Object salt = reflectionSaltSource.getSalt(userDetails);
		// ShaPasswordEncoder算出加密后的数值
		String encPass = shaPasswordEncoder.encodePassword(rawPass, salt);
		return encPass;
	}

	/**
	 * 返回密码是否匹配
	 * 
	 * @param encPass
	 *            SHA-256加密密码
	 * @param rawPass
	 *            未加密密码
	 * @param userDetails
	 * @return
	 */
	public static boolean isPasswordValid(String encPass, String rawPass,
			UserDetails userDetails) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
		ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		// 以用户名作为加密的私钥
		reflectionSaltSource.setUserPropertyToUse("username");
		// 反射调用getUsername()的用户名
		Object salt = reflectionSaltSource.getSalt(userDetails);
		// ShaPasswordEncoder算出加密后的数值
		boolean result = shaPasswordEncoder.isPasswordValid(encPass, rawPass,
				salt);
		return result;
	}

	public static void main(String[] args) {

		QxUsers qxusers= new QxUsers();
		qxusers.setUsername("admin");
		System.out
				.println(isPasswordValid(
						"a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1",
						"admin", qxusers));
	}

}
