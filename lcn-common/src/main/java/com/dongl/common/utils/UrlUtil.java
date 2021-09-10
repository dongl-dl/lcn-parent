

package com.dongl.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public class UrlUtil {
	private final static String ENCODE = "GBK";

	/**
	 * URL 解码
	 */
	public static String getURLDecoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLDecoder.decode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.error("URL解码失败 ex={}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * URL 转码
	 */
	public static String getURLEncoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			log.error("URL转码失败 ex={}", e.getMessage(), e);
		}
		return result;
	}

	public static void main(String[] args) {
		String urlEncoderString = getURLEncoderString("不全为空，也不全为非空（必须是混合状态）不全为空，也不全为非空（必须是混合状态）不全为空，也不全为非空（必须是混合状态）不全为空，也不全为非空（必须是混合状态）不全为空，也不全为非空（必须是混合状态）");
		String urlDecoderString = getURLDecoderString(urlEncoderString);
		System.out.println(urlEncoderString);
		System.out.println(urlDecoderString);
	}
}