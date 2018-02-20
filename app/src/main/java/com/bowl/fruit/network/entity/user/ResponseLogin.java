package com.bowl.fruit.network.entity.user;

import com.bowl.fruit.network.entity.BaseResponse;

/**
 * Created by cathy on 2018/2/8.
 */

public class ResponseLogin extends BaseResponse {
    private int type;
    private String uid;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
