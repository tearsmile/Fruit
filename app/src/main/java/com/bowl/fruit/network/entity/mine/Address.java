package com.bowl.fruit.network.entity.mine;

import java.io.Serializable;

/**
 * Created by CJ on 2018/2/19.
 */

public class Address implements Serializable {
    private String name;
    private String phone;
    private String address;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
