package com.it.j2ee.modules.common.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.modules.common.service.MailService;

@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
//	@Autowired
//	private SMSService smsService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/sms/send")
	@ResponseBody
	public Map<String,String> sendSms(@RequestParam(value = "mobiles[]")String[] mobiles,
			@RequestParam(value = "message")String message){
		Map<String,String> result=new HashMap<String,String>();
		
		try{
//		   smsService.sendSMS(mobiles, message);
		   result.put("code", "success");
		}catch(Exception e){
			logger.info("发送短信失败："+e);
		   result.put("code", "fail");
		}
		return result;
	}
	
	@RequestMapping(value = "/email/send")
	@ResponseBody
	public Map<String,String> sendEmail(@RequestParam(value = "emails[]")String[] emails,
			@RequestParam(value = "subject")String subject,
			@RequestParam(value = "message")String message){
		Map<String,String> result=new HashMap<String,String>();
		
		try{
		   mailService.sendMail(emails, subject, message);
		   result.put("code", "success");
		}catch(Exception e){
			logger.info("发送邮件失败："+e);
		   result.put("code", "fail");
		}
		return result;
	}
	
}
