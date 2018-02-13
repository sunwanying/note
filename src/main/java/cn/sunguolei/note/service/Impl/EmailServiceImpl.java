package cn.sunguolei.note.service.Impl;

import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static cn.sunguolei.note.utils.DesUtil.encrypt;

/**
 * Created by lvyz on 2018/2/12.
 */
@Service
public class EmailServiceImpl implements EmailService {

    /**
     * 给新注册用户发送邮件
     */
    private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final TemplateEngine htmlTemplateEngine;
    private static final String EMAIL_SIMPLE_REGISTER_NAME = "email/register";
    private final JavaMailSender mailSender;
    @Value("${yingnote.key}")
    private String key;

    @Value("${yingnote.email}")
    String emailFrom;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public void sendSimpleRegisterMail(final String recipientEmail, final Locale locale, User user)
            throws Exception {
        String code = user.getUsername()+"_"+user.getActivateCode();
        String url = "http://localhost:8080/user/activeUser?sign=" + encrypt(code, key);
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("userName", user.getUsername());
        ctx.setVariable("url", url);
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("欢迎注册");
        message.setTo(recipientEmail);
        message.setFrom(emailFrom, "YingNote");
        logger.info("接收人邮箱为: {}", recipientEmail);
        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_REGISTER_NAME, ctx);
        message.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
