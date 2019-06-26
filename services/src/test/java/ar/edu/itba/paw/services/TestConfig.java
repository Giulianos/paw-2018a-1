package ar.edu.itba.paw.services;

import ar.edu.itba.paw.services.mocks.DummyMailSenderMock;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({
    "ar.edu.itba.paw.services",
    "ar.edu.itba.paw.persistence"
})
@Configuration
@EnableTransactionManagement
public class TestConfig {

  @Bean
  public DataSource dataSource() {
    final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(org.h2.Driver.class);
    dataSource.setUrl("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1");

    return dataSource;
  }

  @Bean
  public SecurityContext securityContext() {
    final SecurityContext context = new SecurityContext() {
      private Authentication auth;

      @Override
      public Authentication getAuthentication() {
        return auth;
      }

      @Override
      public void setAuthentication(Authentication authentication) {
        this.auth = authentication;
      }
    };

    return context;
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      final EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPackagesToScan("ar.edu.itba.paw.model");
    factoryBean.setDataSource(dataSource());
    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    factoryBean.setJpaVendorAdapter(vendorAdapter);
    final Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    properties.setProperty("format_sql", "true");
    factoryBean.setJpaProperties(properties);
    return factoryBean;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSender mailSender = new DummyMailSenderMock();

    return mailSender;
  }

}