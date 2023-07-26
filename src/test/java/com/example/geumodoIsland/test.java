package com.example.geumodoIsland;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootTest
public class test {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testQuery() {
        String query = "SELECT * FROM emp"; // 실행할 SQL 쿼리
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        // 쿼리 실행 결과를 처리하는 로직 작성
        for (Map<String, Object> row : result) {
            // 각 행의 데이터 처리
            // 예: row.get("column_name");
        }
    }
}
