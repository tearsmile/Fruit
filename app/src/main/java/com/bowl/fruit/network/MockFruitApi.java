package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.fruit.FruitDetailResponse;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.network.entity.user.ResponseLogin;
import com.bowl.fruit.network.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.mock.BehaviorDelegate;
import rx.Observable;

/**
 * Created by cathy on 2018/2/8.
 */

public class MockFruitApi implements FruitApi {

    private BehaviorDelegate<FruitApi> delegate;

    public MockFruitApi(BehaviorDelegate<FruitApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<ResponseLogin> login(User user) {
        return delegate.returningResponse(new ResponseLogin()).login(user);
    }

    @Override
    public Observable<BaseResponse> register(User user) {
        return delegate.returningResponse(new BaseResponse()).register(user);
    }

    @Override
    public Observable<ResponseFruits> getFruitList(int type, int page) {
        ResponseFruits responseFruits = new ResponseFruits();
        List<Fruit> fruits = new ArrayList<>();
        Fruit f = new Fruit("智利蓝莓125g*1盒", "", 12.9, "这么好的蓝莓 都想留给你吃");
        for (int i = 0; i < 5; i++) {
            fruits.add(f);
        }
        responseFruits.setCode(0);
        responseFruits.setMsg("success");
        responseFruits.setFruitList(fruits);
        return delegate.returningResponse(responseFruits).getFruitList(type, page);
    }

    @Override
    public Observable<FruitDetailResponse> getFruitDetail(int id) {
        return null;
    }

    @Override
    public Observable<ResponseOrderNum> getOrderNum(int userId) {
        return null;
    }

    @Override
    public Observable<ResponseOrders> getOrderList(int type) {
        return null;
    }
}
