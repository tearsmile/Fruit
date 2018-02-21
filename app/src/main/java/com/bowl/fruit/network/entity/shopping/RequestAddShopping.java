package com.bowl.fruit.network.entity.shopping;

/**
 * Created by CJ on 2018/2/21.
 */

public class RequestAddShopping {
    private Shopping shopping;
    private String uid;

    public Shopping getShopping() {
        return shopping;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
