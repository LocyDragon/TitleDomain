package com.locydragon.td.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class WebCloud {
	/**
	 * 获取网页内容
	 *
	 * @param url 网页地址
	 * @return 返回内容
	 */
	public static WebResult getStringByWeb(String url) {
		WebResult result = new WebResult();
		StringBuilder webText = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				webText.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			result.result = false;
			result.returned = webText.toString();
			return result;
		}
		result.result = true;
		result.returned = webText.toString();
		return result;
	}
}
