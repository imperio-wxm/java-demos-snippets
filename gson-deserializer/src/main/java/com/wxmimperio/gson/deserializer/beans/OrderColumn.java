package com.wxmimperio.gson.deserializer.beans;

import com.wxmimperio.gson.deserializer.common.DataType;
import com.wxmimperio.gson.deserializer.common.OrderType;

import java.util.List;

public class OrderColumn extends BaseColumn {
    private OrderType orderType;

    public OrderColumn() {
    }

    public OrderColumn(String name, DataType dataType, String comment, List<BaseColumn> baseColumns, OrderType orderType) {
        super(name, dataType, comment, baseColumns);
        this.orderType = orderType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    @Override
    public String toString() {
        return "BaseColumn{" +
                "name='" + super.getName() + '\'' +
                ", dataType=" + super.getDataType() +
                ", comment='" + super.getComment() + '\'' +
                ", baseColumns=" + super.getBaseColumns() +
                ", orderType=" + orderType +
                '}';
    }
}
