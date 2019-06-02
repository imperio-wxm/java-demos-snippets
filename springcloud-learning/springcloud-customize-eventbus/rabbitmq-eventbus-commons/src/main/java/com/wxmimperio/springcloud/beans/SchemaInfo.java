package com.wxmimperio.springcloud.beans;

import java.io.Serializable;

public class SchemaInfo implements Serializable {
    private static final long serialVersionUID = 7743257560365940687L;
    private Long id;
    private String name;

    public SchemaInfo() {
    }

    public SchemaInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SchemaInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
