package cn.sunguolei.note.service;

import cn.sunguolei.note.domain.User;
import org.hibernate.validator.constraints.Email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Created by lvyz on 2018/2/12.
 */
public interface EmailService {
    void sendSimpleRegisterMail(String email, Locale locale, User user) throws MessagingException, UnsupportedEncodingException;
}
