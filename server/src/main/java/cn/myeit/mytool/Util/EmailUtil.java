package cn.myeit.mytool.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param text 邮件内容
     */
    public void send(String to, String subject, String text) throws MessagingException {
        System.out.println("开始发送");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, false);
        mailSender.send(message);
        System.out.println("发送成功");
    }

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param text 邮件内容
     * @param isHtml 是否发送的是html格式的邮件
     */
    public void send(String to, String subject, String text,boolean isHtml) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, isHtml);
        mailSender.send(message);
    }
}
