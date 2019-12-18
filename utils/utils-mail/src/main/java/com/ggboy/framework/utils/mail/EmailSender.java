package com.ggboy.framework.utils.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author zhaizq
 * @date 2019-12-16
 */
public class EmailSender {
    private Session session;

    public EmailSender(String host, String port, String name, String address, String password, String auth, String protocol) {
        Properties prop = new Properties();
        prop.setProperty("mail.from", address);
        prop.setProperty("mail.user", name);
        prop.setProperty("mail.transport.protocol", protocol);

        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.setProperty("mail.smtp.auth", auth);
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        this.session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }

    public void send(EmailContext context) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);

        InternetAddress from = new InternetAddress(session.getProperty("mail.from"), session.getProperty("mail.user"));

        message.setSubject(context.subject, "UTF-8");
        message.setFrom(from);
        message.setRecipients(Message.RecipientType.TO, context.targets.toArray(new InternetAddress[0]));
        message.setContent(context.multipart);
        message.setSentDate(new Date());

        Transport.send(message);
    }
}