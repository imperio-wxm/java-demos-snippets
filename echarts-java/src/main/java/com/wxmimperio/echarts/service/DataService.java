package com.wxmimperio.echarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {

    private JdbcTemplate prodJdbc;
    private JdbcTemplate testJdbc;

    @Autowired
    public DataService(JdbcTemplate jdbcTemplate, @Qualifier("secondJdbcTemplate") JdbcTemplate secondJdbcTemplate) {
        this.prodJdbc = jdbcTemplate;
        this.testJdbc = secondJdbcTemplate;
    }

    public void get() {
        List<Map<String, Object>> result = prodJdbc.queryForList("select * from real_job_list");
        result.forEach(list -> {
            System.out.println(list);
        });

        List<Map<String, Object>> result2 = testJdbc.queryForList("select * from real_job_list");
        result2.forEach(list -> {
            System.out.println(list);
        });
    }
}
