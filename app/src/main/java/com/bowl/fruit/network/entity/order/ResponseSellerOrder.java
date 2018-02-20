package com.bowl.fruit.network.entity.order;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class ResponseSellerOrder extends BaseResponse {
    List<SellerOrder> orders;

    public List<SellerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<SellerOrder> orders) {
        this.orders = orders;
    }
}
