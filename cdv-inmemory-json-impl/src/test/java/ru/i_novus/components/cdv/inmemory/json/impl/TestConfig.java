package ru.i_novus.components.cdv.inmemory.json.impl;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.i_novus.components.cdv.core.service.ValidationRepository;
import ru.i_novus.components.cdv.core.service.ValidationService;
import ru.i_novus.components.cdv.core.dao.JdbcTemplateValidationDao;
import ru.i_novus.components.cdv.core.dao.ValidationDao;
import ru.i_novus.components.cdv.core.service.ValidationServiceImpl;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;
import ru.i_novus.components.cdv.inmemory.json.impl.service.JsonParser;
import ru.i_novus.components.cdv.inmemory.json.impl.service.SpelValidationRepository;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Bean
    public ValidationService<String, ValidationResult> validationService() {
       return new ValidationServiceImpl<>(new JsonParser(), validationRepository());
    }

    @Bean
    public ValidationRepository<String, ValidationResult> validationRepository() {
        return new SpelValidationRepository(validationDao());
    }

    @Bean
    public ValidationDao validationDao() {
        return new JdbcTemplateValidationDao(jdbcTemplate());
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
}
