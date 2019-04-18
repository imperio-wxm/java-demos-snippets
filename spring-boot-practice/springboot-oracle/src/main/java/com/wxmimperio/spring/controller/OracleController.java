package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.beans.Schema;
import com.wxmimperio.spring.service.OracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("oracle")
public class OracleController {

    private OracleService oracleService;

    @Autowired
    public OracleController(OracleService oracleService) {
        this.oracleService = oracleService;
    }

    @GetMapping("tables")
    public List<Schema> getAllTables() {
        return oracleService.getAllTables();
    }
}
