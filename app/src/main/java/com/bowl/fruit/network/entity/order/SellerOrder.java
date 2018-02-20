package com.bowl.fruit.network.entity.order;

/**
 * Created by CJ on 2018/2/19.
 */

public class SellerOrder {
    private long timeStamp;
    private String orderId;
    private String deliverId;
//    private double price;
//    private double discount;
//    private int status;
//    private List<Goods> goods;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(double discount) {
//        this.discount = discount;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public List<Goods> getGoods() {
//        return goods;
//    }
//
//    public void setGoods(List<Goods> goods) {
//        this.goods = goods;
//    }
}
