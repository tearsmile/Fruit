package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.ResponseLogin;
import com.bowl.fruit.network.entity.User;

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
}
