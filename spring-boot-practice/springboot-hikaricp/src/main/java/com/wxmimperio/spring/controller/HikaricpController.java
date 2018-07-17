package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.connection.HikariCpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@RestController
public class HikaricpController {

    private HikariCpConnection hikariCpConnection;

    @Autowired
    public HikaricpController(HikariCpConnection hikariCpConnection) {
        this.hikariCpConnection = hikariCpConnection;
    }

    @GetMapping("test")
    public void test() {
        String sql = "select * from tenant";
        try (Connection connection = hikariCpConnection.getHikariDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
