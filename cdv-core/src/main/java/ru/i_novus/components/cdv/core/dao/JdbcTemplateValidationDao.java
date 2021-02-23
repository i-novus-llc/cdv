package ru.i_novus.components.cdv.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateValidationDao implements ValidationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateValidationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ValidationEntity> findValidationEntityList() {
        return jdbcTemplate.query("select * from cdv.validation where not disabled", new RowMapper<ValidationEntity>() {
            @Override
            public ValidationEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                ValidationEntity entity = new ValidationEntity();
                entity.setId(resultSet.getInt("id"));
                entity.setCode(resultSet.getString("code"));
                entity.setAttribute(resultSet.getString("attr"));
                entity.setLanguage(resultSet.getString("lang"));
                entity.setExpression(resultSet.getString("expr"));
                entity.setMessage(resultSet.getString("msg"));
                return entity;
            }
        });
    }
}
