package com.it.j2ee.modules.common.service;

import java.util.Date;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.it.j2ee.modules.common.exception.MessageSendException;

import freemarker.template.Template;

/**
 * 发送邮件服务
 * Created by leihong on 2014/12/1.
 */
@Service
public class MailService {

    private Logger logger= LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 根据模板发送邮件
     * @param to 接受人地址数组
     * @param subject 邮件主题
     * @param templateName 模板名称（以.ftl结尾）
     * @param pram 模板参数hashMap，用于替换模板中的${}标签，例如在todomail.ftl模板中，key="receiver",value="张三"
     */
    public void sendMail(String[] to,String subject,String templateName,Map<String,String> pram)throws MessageSendException {

        MimeMessage msg = mailSender.createMimeMessage();
        try {
            Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            if(tpl==null){
                logger.error("找不到模板文件"+templateName);
                throw new MessageSendException("根据模板名称："+templateName+"找不到模板文件");
            }

            //FreeMarker通过Map传递动态数据，解析模板并替换动态数据，将替换模板文件中的${}标签。
            String htmlText= FreeMarkerTemplateUtils.processTemplateIntoString(tpl, pram);

            MimeMessageHelper mmHelper = new MimeMessageHelper(msg, false,"utf8");
            mmHelper.setFrom(mailSender.getUsername());
            mmHelper.setSubject(subject);
            mmHelper.setText(htmlText,true);
            mmHelper.setSentDate(new Date());
            mmHelper.setTo(to);

            mailSender.send(msg);
        }catch (Exception e){
            logger.error("发送邮件出错"+e);
            throw new  MessageSendException("发送邮件出错！"+e);
        }
    }

    /**
     * 自定义内容发送邮件
     * @param to 收件人地址数据
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String[] to,String subject,String content){
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mmHelper = new MimeMessageHelper(msg, false, "utf8");
            mmHelper.setFrom(mailSender.getUsername());
            mmHelper.setSubject(subject);
            mmHelper.setText(content);
            mmHelper.setSentDate(new Date());
            mmHelper.setTo(to);

            mailSender.send(msg);
        }catch (Exception e){
            logger.error("发送邮件出错"+e);
            throw new  MessageSendException("发送邮件出错！"+e);
        }
    }
}
