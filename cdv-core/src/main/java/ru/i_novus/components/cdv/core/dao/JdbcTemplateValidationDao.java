package ru.i_novus.components.cdv.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateValidationDao implements ValidationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateValidationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ValidationEntity> findValidationEntityList() {
        return jdbcTemplate.query("select * from cdv.validation where not disabled",
                (resultSet, i) -> toValidationEntity(resultSet));
    }

    private ValidationEntity toValidationEntity(ResultSet resultSet) throws SQLException {

        ValidationEntity entity = new ValidationEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setCode(resultSet.getString("code"));
        entity.setAttribute(resultSet.getString("attr"));
        entity.setLanguage(resultSet.getString("lang"));
        entity.setExpression(resultSet.getString("expr"));
        entity.setMessage(resultSet.getString("msg"));

        return entity;
    }
}
