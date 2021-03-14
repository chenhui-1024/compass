package com.ch.compass.core.persistence.mapper;

import com.ch.compass.core.model.Variable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VariablesTypeHandler implements TypeHandler<List<Variable>> {
    private static final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<Variable> variables, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setString(i, objMapper.writeValueAsString(variables));
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Variable> getResult(ResultSet resultSet, String s) throws SQLException {
        try {
            return objMapper.readValue(resultSet.getString(s), new TypeReference<List<Variable>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Variable> getResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return objMapper.readValue(resultSet.getString(i), new TypeReference<List<Variable>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Variable> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
