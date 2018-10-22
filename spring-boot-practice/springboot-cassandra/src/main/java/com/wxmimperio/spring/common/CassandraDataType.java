package com.wxmimperio.spring.common;


import com.datastax.driver.core.DataType;

public enum CassandraDataType {
    DATE, TIMESTAMP, BOOLEAN, DOUBLE, FLOAT, INT, BIGINT, TEXT, TIME, UUID, INET, ASCII, DECIMAL,
    VARCHAR, VARINT, TIMEUUID, TINYINT, SMALLINT, COUNTER, BLOB;

    public static DataType getDataTypeByName(CassandraDataType cassandraDataType) {
        switch (cassandraDataType) {
            case DATE:
                return DataType.date();
            case TIMESTAMP:
                return DataType.timestamp();
            case BOOLEAN:
                return DataType.cboolean();
            case DOUBLE:
                return DataType.cdouble();
            case FLOAT:
                return DataType.cfloat();
            case INT:
                return DataType.cint();
            case BIGINT:
                return DataType.bigint();
            case TEXT:
                return DataType.text();
            case TIME:
                return DataType.time();
            case UUID:
                return DataType.uuid();
            case INET:
                return DataType.inet();
            case ASCII:
                return DataType.ascii();
            case DECIMAL:
                return DataType.decimal();
            case VARCHAR:
                return DataType.varchar();
            case VARINT:
                return DataType.varint();
            case TIMEUUID:
                return DataType.timeuuid();
            case TINYINT:
                return DataType.tinyint();
            case SMALLINT:
                return DataType.smallint();
            case COUNTER:
                return DataType.counter();
            case BLOB:
                return DataType.blob();
            default:
                throw new RuntimeException("Can not get this type = " + cassandraDataType);
        }
    }
}
