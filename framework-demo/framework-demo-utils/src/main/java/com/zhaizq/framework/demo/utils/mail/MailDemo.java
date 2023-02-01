package com.zhaizq.framework.demo.utils.mail;

import com.zhaizq.framework.utils.mail.EmailContext;
import com.zhaizq.framework.utils.mail.EmailSender;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public class MailDemo {
    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        EmailSender emailSender = new EmailSender("smtp.163.com", "465", "名字", "xxx@163.com", "xxx", "true", "smtp");
        emailSender.send(EmailContext.create("测试邮件").setContent("测试内容").addTarget("xxx"));
    }
}