package com.dongl.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName CustomSystemUtil.java
 * @Description 系统工具类，用于获取系统相关信息
 * @createTime 2021-09-09 09:46:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomSystemUtil {
	/**
	 * 内网IP
	 */
	private static String INTRANET_IP = getIntranetIp();

	/**
	 * 外网IP
	 */
	private static String INTERNET_IP = getInternetIp();

	/**
	 * 获得内网IP
	 *
	 * @return 内网IP
	 */
	private static String getIntranetIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得外网IP
	 *
	 * @return 外网IP
	 */
	private static String getInternetIp() {
		try {
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			Enumeration<InetAddress> addrs;
			while (networks.hasMoreElements()) {
				addrs = networks.nextElement().getInetAddresses();
				while (addrs.hasMoreElements()) {
					ip = addrs.nextElement();
					if (ip != null
							&& ip instanceof Inet4Address
							&& ip.isSiteLocalAddress()
							&& !ip.getHostAddress().equals(INTRANET_IP)) {
						return ip.getHostAddress();
					}
				}
			}

			// 如果没有外网IP，就返回内网IP
			return INTRANET_IP;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String intranetIp = getIntranetIp();
		String internetIp = getInternetIp();

		System.out.println("内网IP: " + internetIp);
		System.out.println("外网IP: " + intranetIp);
	}
}