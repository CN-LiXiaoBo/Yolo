package com.sicnu.yolo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @className: MailUtils
 * @description: 发送邮件
 * @author: 热爱生活の李
 * @since: 2022/7/2 17:10
 */
@Component
public class MailUtils {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private TemplateEngine templateEngine;
    private String REGISTER_PATH = "http://127.0.0.1/user/active?uuid=";

    /**
     * @param receiver 接收的人
     * @param text 标题
     * @param content 内容
     * @throws Exception
     */
    public  void sendEmail(String receiver,String text,String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(text);
        helper.setText(content,true);
        mailSender.send(mimeMessage);
    }
    public void sendHtmlMail(String receiver,String text,String uuid) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(text);
        Context context = new Context();
        context.setVariable("email",receiver);
        context.setVariable("url",REGISTER_PATH+uuid);
        String process = templateEngine.process("active", context);
        helper.setText(process,true);
        mailSender.send(mimeMessage);
    }
}
