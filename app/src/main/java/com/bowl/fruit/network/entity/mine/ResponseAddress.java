package com.bowl.fruit.network.entity.mine;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class ResponseAddress extends BaseResponse {
    private List<Address> addressList;

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
