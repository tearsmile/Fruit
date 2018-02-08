package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.ResponseLogin;
import com.bowl.fruit.network.entity.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by cathy on 2018/2/8.
 */

public interface FruitApi {
    @POST
    Observable<ResponseLogin> login(@Body User user);
}
