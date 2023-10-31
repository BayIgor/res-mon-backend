package bay.university.resmon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // Порт вашего SMTP-сервера
        mailSender.setUsername("resourcemonitortech@gmail.com");
        mailSender.setPassword("vovlmwknmndpgtac");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}