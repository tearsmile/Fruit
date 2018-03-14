package com.bowl.fruit.network.entity.shopping;

import com.bowl.fruit.network.entity.fruit.Fruit;

/**
 * Created by CJ on 2018/2/21.
 */

public class Shopping {
    private String id;
    private String name;
    private String desc;
    private double price;
    private double discount;
    private int num;
    private String pic;
    private boolean select = false;

    public Shopping(String name, String pic, double price, String desc) {
        this.name = name;
        this.pic = pic;
        this.price = price;
        this.desc = desc;
    }

    public Shopping(Fruit fruit){
        id = fruit.getId();
        name = fruit.getName();
        desc = fruit.getDesc();
        price = fruit.getPrice();
        discount = fruit.getDiscount();
        num = 1;
//        pic = fruit.getPic().get(0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
