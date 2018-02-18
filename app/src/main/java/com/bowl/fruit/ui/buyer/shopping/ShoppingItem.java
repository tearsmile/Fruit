package com.bowl.fruit.ui.buyer.shopping;

import com.bowl.fruit.network.entity.fruit.Fruit;

/**
 * Created by CJ on 2018/2/18.
 */

public class ShoppingItem extends Fruit {
    private boolean select = false;

    public ShoppingItem(String name, String pic, double price, String desc) {
        super(name, pic, price, desc);
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
