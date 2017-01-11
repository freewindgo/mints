package com.mints.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 加密工具类类
 * 
 * @author Justin
 * @date 2017年1月10日
 */
public class EncryptUtil {
	private static final Logger log = LogManager.getLogger(EncryptUtil.class.getName());

	/**
	 * 获取加密结果（MD5 SHA-256）
	 * 
	 * @param source
	 * @param encType
	 *            加密类型MD5 SHA-256
	 * @return
	 */
	public static String getEncryptResult(String source, String encType) {
		try {
			String s = encrypt(source, encType);
			return s;
		} catch (NoSuchAlgorithmException e) {
			log.debug("加密失败(算法类型错误)：" + e);
			return null;
		}

	}
	
	/**
	 * 多次加密（MD5 SHA-256）
	 * 
	 * @param source
	 * @param encType
	 * @param times
	 * @return
	 */
	public static String getEncryptResult(String source, String encType, int times) {
		try {
			String s = "";
			for (int i = 0; i < times; i++) {
				s = encrypt(source, encType);
			}
			return s;
		} catch (NoSuchAlgorithmException e) {
			log.debug("加密失败(算法类型错误)：" + e);
			return null;
		}
	}

	private static String encrypt(String source, String encType) throws NoSuchAlgorithmException {
		byte[] bt = source.getBytes();
		MessageDigest md = MessageDigest.getInstance(encType);
		md.update(bt);
		String strDes = bytes2Hex(md.digest()); // to HexString
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		StringBuffer des = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des.append("0");
			}
			des.append(tmp);
		}
		return des.toString();
	}

	/**
	 * 加密验证（MD5 SHA-256）
	 * @param source
	 * @param encryptCode
	 * @param encryptType
	 * @return
	 */
	public static boolean isVerify(String source, String encryptCode, String encType) {
		return getEncryptResult(source, encType).equals(encryptCode);
	}
	
	/**
	 * 多次加密验证（MD5 SHA-256）
	 * @param source
	 * @param encryptCode
	 * @param encType
	 * @param times
	 * @return
	 */
	public static boolean isVerify(String source, String encryptCode, String encType, int times) {
		return getEncryptResult(source, encType, times).equals(encryptCode);
	}

	


}
