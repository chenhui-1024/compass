package com.ch.compass.core.persistence.mapper;

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
import java.util.Map;
import java.util.Set;

public class MapTypeHandler implements TypeHandler<Map<String, Object>> {
    private static final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Map<String, Object> map, JdbcType jdbcType) throws SQLException {

        try {
            preparedStatement.setString(i, objMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getResult(ResultSet resultSet, String s) throws SQLException {
        try {
            return objMapper.readValue(resultSet.getString(s), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return objMapper.readValue(resultSet.getString(i), new TypeReference<Set<String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String, Object> getResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            return objMapper.readValue(callableStatement.getString(i), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
