package tw.appworks.school.example.stylish.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tw.appworks.school.example.stylish.model.user.User;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class MailGunServiceImpl {
    private static final String TEMPLATE_NAME = "registration";
    private static final String SPRING_LOGO_IMAGE = "templates/images/logo.png";
    private static final String PNG_MIME = "image/png";
    private static final String MAIL_SUBJECT = "Registration Confirmation";
    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public MailGunServiceImpl(Environment environment, JavaMailSender mailSender, TemplateEngine htmlTemplateEngine) {
        this.environment = environment;
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public String sendRegisterMail(User user)
            throws MessagingException, UnsupportedEncodingException {
        try {
            String confirmationUrl = "https://chouyu.site/";
            String mailFrom = environment.getProperty("spring.mail.properties.mail.smtp.from");
            String mailFromName = environment.getProperty("mail.from.name", "Identity");
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper email;
            email = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//        email.setTo(user.getEmail());
//            email.setTo("xppp3082@gmail.com");
            email.setTo("leslie20100430@gmail.com");
            email.setSubject(MAIL_SUBJECT);
            email.setFrom(new InternetAddress(mailFrom, mailFromName));

            final Context ctx = new Context(LocaleContextHolder.getLocale());
//        ctx.setVariable("email", "xppp3082@gmail.com");
            ctx.setVariable("email", user.getEmail());
            ctx.setVariable("name", user.getName());
            ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);
            ctx.setVariable("url", confirmationUrl);

            final String htmlContent = this.htmlTemplateEngine.process(TEMPLATE_NAME, ctx);
            email.setText(htmlContent, true);

            ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
            email.addInline("springLogo", clr, PNG_MIME);
            mailSender.send(mimeMessage);
            return "User created successfully";
        } catch (Exception e) {
            return "User created successfully failed";
        }
    }
}
