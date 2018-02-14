package com.bowl.fruit.network.entity.fruit;

/**
 * Created by cathy on 2018/2/11.
 */

public class Fruit {
    private String mName;
    private String mPic;
    private double mPrice;
    private String mDesc;

    public Fruit(String name, String pic, double price, String desc){
        mName = name;
        mPic = pic;
        mPrice = price;
        mDesc = desc;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPic() {
        return mPic;
    }

    public void setPic(String mPic) {
        this.mPic = mPic;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}
