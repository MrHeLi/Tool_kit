package com.kiven.tools;

import com.kiven.tools.logutils.Logger;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;


/*******************************************************************************
 * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
 ******************************************************************************/
public class MD5 {
	/**
	 * 用MD5算法加密
	 * @param in String : 待加密的原文
	 * @return String : 加密后的密文，如果原文为空，则返回null;
	 */
	public static String encode(final String in){
		return encode(in, "");
	}
	/**
	 * 用MD5算法加密
	 * @param in String : 待加密的原文
	 * @param charset String : 加密算法字符集
	 * @return String : 加密后的密文，若出错或原文为null，则返回null
	 */
	public static String encode(final String in, final String charset){
		if(in == null) return null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(charset==null || "".equals(charset)){
				md.update(in.getBytes());
			}else{
				try{
					md.update(in.getBytes(charset));
				}catch(Exception e){
					md.update(in.getBytes());
				}
			}
			byte[] digesta = md.digest();
			return byte2hex(digesta);
		}catch(java.security.NoSuchAlgorithmException ex){
			//出错
			Logger.i("encode("+in+","+charset+"):NoSuchAlgorithmException -->"+ex.getMessage());
			return null;
		}
	}
	private static String byte2hex(final byte[] b){
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++){
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1){
				hs = hs + "0" + stmp;
			}else{
				hs = hs + stmp;
			}
		}
		return hs;
	}
	public MD5(){
	}
}
