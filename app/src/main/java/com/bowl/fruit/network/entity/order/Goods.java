package com.bowl.fruit.network.entity.order;

import java.io.Serializable;

/**
 * Created by CJ on 2018/2/19.
 */

public class Goods implements Serializable {
    private String id;
    private String name;
    private int num;
    private String pic;

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
}
