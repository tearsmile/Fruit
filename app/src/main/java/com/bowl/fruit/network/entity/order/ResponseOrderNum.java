package com.bowl.fruit.network.entity.order;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class ResponseOrderNum extends BaseResponse {
    private List<Integer> nums;

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }
}
