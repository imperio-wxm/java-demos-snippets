package com.wxmimperio.spring.beans;

import com.wxmimperio.spring.common.SchemaType;

import java.util.List;

public class Schema {

    private String name;
    private SchemaType schemaType;
    private String comment;
    private List<Column> columns;

    public Schema() {
    }

    public Schema(String name, SchemaType schemaType, String comment) {
        this.name = name;
        this.schemaType = schemaType;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchemaType getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(SchemaType schemaType) {
        this.schemaType = schemaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", schemaType=" + schemaType +
                ", comment='" + comment + '\'' +
                ", columns=" + columns +
                '}';
    }
}
