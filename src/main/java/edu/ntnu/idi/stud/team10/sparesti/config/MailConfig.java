package edu.ntnu.idi.stud.team10.sparesti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/** Configuration for sending emails. */
@Configuration
public class MailConfig {
  private final Environment env;

  public MailConfig(Environment env) {
    this.env = env;
  }

  /**
   * Configure the JavaMailSender.
   *
   * @return JavaMailSender
   */
  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(env.getProperty("SMTP_HOST"));
    mailSender.setPort(587);
    mailSender.setUsername(env.getProperty("SMTP_EMAIL"));
    mailSender.setPassword(env.getProperty("SMTP_PASSWORD"));

    mailSender.setProtocol("smtp");

    return mailSender;
  }
}
