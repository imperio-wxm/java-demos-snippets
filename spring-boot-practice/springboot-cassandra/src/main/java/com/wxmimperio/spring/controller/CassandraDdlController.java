package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.common.CassandraDataType;
import com.wxmimperio.spring.service.CassandraDdlService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listKeyspace")
    public List<String> listKeyspace() {
        return cassandraDdlService.listKeyspaces();
    }

    @GetMapping("/tables/{keyspace}")
    public List<String> listKeyspaceTables(@PathVariable String keyspace) {
        return cassandraDdlService.listKeyspaceTables(keyspace);
    }

    @DeleteMapping("/dropTable/{keyspace}/{tableName}")
    public String dropTable(@PathVariable String keyspace, @PathVariable String tableName) {
        return cassandraDdlService.dropTable(keyspace, tableName);
    }

    @PostMapping("/createKeyspace/{keyspace}")
    public String createKeyspace(@PathVariable String keyspace) {
        return cassandraDdlService.createKeyspace(keyspace);
    }

    @DeleteMapping("/dropKeyspace/{keyspace}")
    public String dropKeyspace(@PathVariable String keyspace) {
        return cassandraDdlService.dropKeyspace(keyspace);
    }

    @DeleteMapping("/truncateTable/{keyspace}/{tableName}")
    public String truncateTable(@PathVariable String keyspace, @PathVariable String tableName) {
        return cassandraDdlService.truncateTable(keyspace, tableName);
    }

    @PutMapping("/renameColumn/{keyspace}/{tableName}/{oldName}/{newName}")
    public String renameColumn(@PathVariable String keyspace, @PathVariable String tableName, @PathVariable String oldName, @PathVariable String newName) {
        return cassandraDdlService.renameColumn(keyspace, tableName, oldName, newName);
    }

    @PostMapping("/addColumn/{keyspace}/{tableName}/{colName}")
    public String addColumn(@PathVariable String keyspace, @PathVariable String tableName, @PathVariable String colName, @RequestParam CassandraDataType cassandraDataType) {
        return cassandraDdlService.addColumn(keyspace, tableName, colName, cassandraDataType);
    }

    @PutMapping("/changeColumnType/{keyspace}/{tableName}/{colName}")
    public String changeColumnType(@PathVariable String keyspace, @PathVariable String tableName, @PathVariable String colName, @RequestParam CassandraDataType newType) {
        return cassandraDdlService.changeColumnType(keyspace, tableName, colName, newType);
    }

    @DeleteMapping("/dropColumn/{keyspace}/{tableName}/{colName}")
    public String dropColumn(@PathVariable String keyspace, @PathVariable String tableName, @PathVariable String colName) {
        return cassandraDdlService.dropColumn(keyspace, tableName, colName);
    }

    @GetMapping("/dropColumn/{keyspace}/{tableName}")
    public void getTableDetails(@PathVariable String keyspace, @PathVariable String tableName) {
        cassandraDdlService.getTableDetails(keyspace, tableName);
    }

    @PutMapping("/alterTableOptions/{keyspace}/{tableName}")
    public void alterTableOptions(@PathVariable String keyspace, @PathVariable String tableName) {
        cassandraDdlService.alterTableOptions(keyspace, tableName);
    }
}
