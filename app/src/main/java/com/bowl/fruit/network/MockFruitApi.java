package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.ResponseLogin;
import com.bowl.fruit.network.entity.fruit.FruitDetailResponse;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.network.entity.user.User;

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
    public Observable<ResponseFruits> getFruitList() {
        return null;
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
