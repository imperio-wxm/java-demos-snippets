package com.wxmimperio.spring.service;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.bean.TableOne;
import com.wxmimperio.spring.bean.TableThree;
import com.wxmimperio.spring.bean.TableTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveTableOne(List<TableOne> tableOnes) {
        String sql = "INSERT INTO `my_test`.`table_1` VALUES(?,?)";
        jdbcTemplate.batchUpdate(sql, buildObj(tableOnes));
    }

    private List<Object[]> buildObj(List<TableOne> table) {
        List<Object[]> obj = Lists.newArrayList();
        table.forEach(t -> obj.add(new Object[]{t.getId(), t.getName()}));
        return obj;
    }

    public void saveTableTwo(List<TableTwo> tableTwos) {
        String sql = "INSERT INTO `my_test`.`table_2` VALUES(?,?)";
        jdbcTemplate.batchUpdate(sql, buildObj2(tableTwos));
    }

    private List<Object[]> buildObj2(List<TableTwo> table) {
        List<Object[]> obj = Lists.newArrayList();
        table.forEach(t -> obj.add(new Object[]{t.getId(), t.getAge()}));
        return obj;
    }

    public void saveTableThree(List<TableThree> tableThrees) {
        String sql = "INSERT INTO `my_test`.`table_3` VALUES(?,?)";
        jdbcTemplate.batchUpdate(sql, buildObj3(tableThrees));
        throw new RuntimeException("dfasdfas");
    }

    private List<Object[]> buildObj3(List<TableThree> table) {
        List<Object[]> obj = Lists.newArrayList();
        table.forEach(t -> obj.add(new Object[]{t.getId(), t.getJob()}));
        return obj;
    }
}
