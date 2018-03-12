package com.bowl.fruit.network.entity.user;

import com.bowl.fruit.network.entity.BaseResponse;

/**
 * Created by CJ on 2018/2/20.
 */

public class ResponseRegister extends BaseResponse {
    private String uid;

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
    }
}
