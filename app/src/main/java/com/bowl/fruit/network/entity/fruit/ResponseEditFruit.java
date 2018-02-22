package com.bowl.fruit.network.entity.fruit;

import com.bowl.fruit.network.entity.BaseResponse;

/**
 * Created by CJ on 2018/2/21.
 */

public class ResponseEditFruit extends BaseResponse {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
