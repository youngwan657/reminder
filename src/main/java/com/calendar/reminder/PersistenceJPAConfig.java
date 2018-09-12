package com.calendar.reminder;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableJpaRepositories//(value = "com.calendar.reminder")
//@EnableTransactionManagement
//public class PersistenceJPAConfig {
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(new String[]{"com.calendar.reminder"});
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(additionalProperties());
//
//        return em;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://fyyknypscirzdg:btRK9jnZj1qcqOrZe8OBdnJjNn@ec2-54-163-228-109.compute-1.amazonaws.com:5432/dfcomeboave3jd");
//        dataSource.setUsername("fyyknypscirzdg");
//        dataSource.setPassword("btRK9jnZj1qcqOrZe8OBdnJjNn");
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(
//            EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//
//        return properties;
//    }
//}
//

//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@Configuration
////@ComponentScan(basePackages = "com.calendar.reminder")
//public class PersistenceJPAConfig {
//
//    @Bean
//    public BasicDataSource dataSource() throws URISyntaxException {
//        URI dbUri = new URI("postgres://fyyknypscirzdg:btRK9jnZj1qcqOrZe8OBdnJjNn@ec2-54-163-228-109.compute-1.amazonaws.com:5432/dfcomeboave3jd");
//
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
//
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setUrl(dbUrl);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//
//        return basicDataSource;
//    }
//}
