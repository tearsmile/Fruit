package com.bowl.fruit.network.entity.shopping;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by CJ on 2018/2/21.
 */

public class ResponseShopping extends BaseResponse {
    private List<Shopping> shoppingList;

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Shopping> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
