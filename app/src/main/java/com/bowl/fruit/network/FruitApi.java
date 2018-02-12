package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.ResponseLogin;
import com.bowl.fruit.network.entity.fruit.FruitDetailResponse;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.network.entity.user.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cathy on 2018/2/8.
 */

public interface FruitApi {
    @POST
    Observable<ResponseLogin> login(@Body User user);

    @POST
    Observable<ResponseFruits> getFruitList();

    @POST
    Observable<FruitDetailResponse> getFruitDetail(@Path("id") int id);

    @POST
    Observable<ResponseOrderNum> getOrderNum(@Path("uid") int userId);

    @POST
    Observable<ResponseOrders> getOrderList(@Path("type") int type);

}
