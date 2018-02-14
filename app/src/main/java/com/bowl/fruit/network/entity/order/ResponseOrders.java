package com.bowl.fruit.network.entity.order;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class ResponseOrders extends BaseResponse {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
