package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/OAuth")
public class WeixinController {
	
	@RequestMapping(value = "/weixin/validate")
	@ResponseBody
	public String validate(@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr) {
		System.out.println("进去了...");
		System.out.println("signature:"+signature+"\ttimestamp:"+timestamp+"\tnonce:"+nonce+"\techostr:"+echostr);
		return echostr;
	}
}
