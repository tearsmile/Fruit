package com.bowl.fruit.network.entity;

import java.util.List;

/**
 * Created by cathy on 2018/2/11.
 */

public class ResponseFruits extends BaseResponse {
    private List<Fruit> fruitList;

    public List<Fruit> getFruitList() {
        return fruitList;
    }

    public void setFruitList(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }
}
