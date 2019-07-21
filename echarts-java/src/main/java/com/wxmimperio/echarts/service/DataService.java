package com.wxmimperio.echarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {

    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate secondJdbcTemplate;

    @Autowired
    public DataService(JdbcTemplate jdbcTemplate, @Qualifier("secondJdbcTemplate") JdbcTemplate secondJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.secondJdbcTemplate = secondJdbcTemplate;
    }

    public void get() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from real_job_list");
        result.forEach(list -> {
            System.out.println(list);
        });

        List<Map<String, Object>> result2 = secondJdbcTemplate.queryForList("select * from real_job_list");
        result2.forEach(list -> {
            System.out.println(list);
        });
    }
}
