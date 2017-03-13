package com.snda.dw.pojo;

/**
 * Created by weiximing.imperio on 2017/2/24.
 */
public class Bslog {

    private String pt_id;
    private String order_id;
    private String deposit_time;
    private String deposit_value;

    public String getPt_id() {
        return pt_id;
    }

    public void setPt_id(String pt_id) {
        this.pt_id = pt_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDeposit_time() {
        return deposit_time;
    }

    public void setDeposit_time(String deposit_time) {
        this.deposit_time = deposit_time;
    }

    public String getDeposit_value() {
        return deposit_value;
    }

    public void setDeposit_value(String deposit_value) {
        this.deposit_value = deposit_value;
    }

    @Override
    public String toString() {
        return "Bslog{" +
                "pt_id='" + pt_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", deposit_time='" + deposit_time + '\'' +
                ", deposit_value='" + deposit_value + '\'' +
                '}';
    }
}
