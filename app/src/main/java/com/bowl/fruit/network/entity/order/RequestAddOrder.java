package com.bowl.fruit.network.entity.order;

/**
 * Created by CJ on 2018/2/21.
 */

public class RequestAddOrder {
    private String uid;
    private Order order;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
