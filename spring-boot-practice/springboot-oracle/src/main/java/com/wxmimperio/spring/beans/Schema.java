package com.wxmimperio.spring.beans;

import com.wxmimperio.spring.common.SchemaType;

public class Schema {

    private String name;
    private SchemaType schemaType;
    private String comment;

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

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", schemaType=" + schemaType +
                ", comment='" + comment + '\'' +
                '}';
    }
}
