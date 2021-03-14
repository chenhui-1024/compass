package com.ch.compass.core.persistence.mapper;

import com.ch.compass.core.model.DecisionTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DecisionTableMapper {
    void insertOne(DecisionTable decisionTable);

    int updateOne(DecisionTable decisionTable);

    DecisionTable selectOne(String id);

    int deleteOne(String id);

    List<DecisionTable> selectMany(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("orderBy") String orderBy,
            @Param("order") String order
    );

    int count();
}
