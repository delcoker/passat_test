package com.deloop.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
//@Import(DBConfiguration.class)
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String email_host;

    @Value("${spring.mail.port}")
    private int email_port;

    @Value("${spring.mail.username}")
    private String email_username;

    @Value("${spring.mail.password}")
    private String email_password;

    @Value("${spring.mail.protocol}")
    private String email_protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean email_enable_auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean email_enable_tls;

    @Value("${spring.mail.properties.mail.debug}")
    private boolean email_enable_debug;

    @Bean
    public JavaMailSender getJavaGMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(email_host);
        mailSender.setPort(email_port);

        mailSender.setUsername(email_username);
        mailSender.setPassword(email_password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", email_protocol);
        props.put("mail.smtp.auth", email_enable_auth);
        props.put("mail.smtp.starttls.enable", email_enable_tls);
        props.put("mail.debug", email_enable_debug);

//        System.err.println(email_host);
//        System.err.println(email_port);
//        System.err.println(email_username);
//        System.err.println(email_password);
//        System.err.println(email_protocol);
//        System.err.println(email_enable_auth);

        return mailSender;
    }

}
