package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.service.CassandraDdlService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CassandraDdlController {

    private CassandraDdlService cassandraDdlService;

    public CassandraDdlController(CassandraDdlService cassandraDdlService) {
        this.cassandraDdlService = cassandraDdlService;
    }

    @PostMapping("/createTable")
    public String createTable(@RequestParam String sql) {
        cassandraDdlService.createTable(sql);
        return sql;
    }

    @DeleteMapping("/dropDefaultKeyspaceTable/{tableName}")
    public String dropDefaultKeyspaceTable(@PathVariable String tableName) {
        cassandraDdlService.dropDefaultKeyspaceTable(tableName);
        return tableName;
    }

    @DeleteMapping("/dropTable/{keyspace}/{tableName}")
    public String dropTable(@PathVariable String keyspace, @PathVariable String tableName) {
        return cassandraDdlService.dropTable(keyspace, tableName);
    }

    @PostMapping("/createKeyspace/{keyspace}")
    public String createKeyspace(@RequestParam String keyspace) {
        cassandraDdlService.createKeyspace(keyspace);
        return keyspace;
    }

    @DeleteMapping("/deleteKeyspace/{keyspace}")
    public String deleteKeyspace(@RequestParam String keyspace) {
        cassandraDdlService.dropKeyspace(keyspace);
        return keyspace;
    }
}
