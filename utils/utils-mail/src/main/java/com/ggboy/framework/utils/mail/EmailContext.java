package com.ggboy.framework.utils.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class EmailContext {
    List<InternetAddress> targets = new LinkedList<>();
    String subject = "无标题";
    MimeMultipart multipart = new MimeMultipart();

    private EmailContext() {
    }

    public static EmailContext create(String subject) {
        EmailContext abc = new EmailContext();
        abc.subject = subject;
        return abc;
    }

    public EmailContext addTarget(String... targets) throws AddressException {
        for (String target : targets) {
            this.targets.add(new InternetAddress(target));
        }
        return this;
    }

    public EmailContext setContent(String content) throws MessagingException {
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(content, "text/html;charset=UTF-8");
        return addBodyPart(body);
    }

    public EmailContext addAnnex(String fileName, File file) throws MessagingException, UnsupportedEncodingException {
        return addAnnex(fileName, new FileDataSource(file));
    }

    public EmailContext addAnnex(String fileName, byte[] data) throws MessagingException, UnsupportedEncodingException {
        return addAnnex(fileName, new ByteArrayDataSource(data, "application/octet-stream"));
    }

    private EmailContext addAnnex(String fileName, DataSource dataSource) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart body = new MimeBodyPart();
        body.setDataHandler(new DataHandler(dataSource));
        body.setFileName(MimeUtility.encodeText(fileName));
        return addBodyPart(body);
    }

    public EmailContext addBodyPart(MimeBodyPart body) throws MessagingException {
        multipart.addBodyPart(body);
        return this;
    }
}