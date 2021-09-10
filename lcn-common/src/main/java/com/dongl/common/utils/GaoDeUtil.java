
package com.dongl.common.utils;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.dongl.common.entity.dto.GaodeLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author D-L
 * @version 1.0.0
 * @ClassName GaoDeUtil.java
 * @Description 根据IP获取省份和城市   注意这里的ip不能为局域网的ip地址
 * @createTime 2021-08-26 09:22:00
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GaoDeUtil {

	/**
	 * Gets city by ip addr.
	 *
	 * @param ipAddr the ip addr
	 *
	 * @return the city by ip addr
	 */
	public static GaodeLocation getCityByIpAddr(String ipAddr) {
		// http://lbs.amap.com/api/webservice/guide/api/ipconfig/
		log.info("getCityByIpAddr - 根据IP定位. ipAddr={}", ipAddr);
		GaodeLocation location = null;
		String urlAddressIp = "https://restapi.amap.com/v3/ip?key=f8bdce6f882a98635bb0b7b897331327&ip=%s";
		String url = String.format(urlAddressIp, ipAddr);
		try {
			String str = HttpClientUtil.get(HttpConfig.custom().url(url));
			location = new ObjectMapper().readValue(str, GaodeLocation.class);
		} catch (Exception e) {
			log.error("getCityByIpAddr={}", e.getMessage(), e);
		}
		log.info("getCityByIpAddr - 根据IP定位. ipAddr={}, location={}", ipAddr, location);
		return location;
	}

	public static void main(String[] args) {
		GaodeLocation cityByIpAddr = getCityByIpAddr("110.184.229.98");
		System.out.println(cityByIpAddr);
	}
}
