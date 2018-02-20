package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.user.ResponseLogin;
import com.bowl.fruit.network.entity.fruit.FruitDetailResponse;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.network.entity.user.User;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by cathy on 2018/2/8.
 */

public interface FruitApi {

    @POST("/login")
    Observable<ResponseLogin> login(@Body User user);

    @POST("/register")
    Observable<BaseResponse> register(@Body User user);

    @POST("/fruitList")
    Observable<ResponseFruits> getFruitList(@Field("type") int type, @Field("page") int page);

    @POST("/login")
    Observable<FruitDetailResponse> getFruitDetail(@Field("id") int id);

    @POST("/login")
    Observable<ResponseOrderNum> getOrderNum(@Field("uid") int userId);

    @POST("/login")
    Observable<ResponseOrders> getOrderList(@Field("type") int type);

}
