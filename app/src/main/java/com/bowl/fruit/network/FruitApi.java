package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.fruit.ResponseEditFruit;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.message.ResponseMessage;
import com.bowl.fruit.network.entity.mine.RequestAddress;
import com.bowl.fruit.network.entity.mine.ResponseAddress;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.network.entity.shopping.RequestAddShopping;
import com.bowl.fruit.network.entity.shopping.ResponseShopping;
import com.bowl.fruit.network.entity.user.ResponseLogin;
import com.bowl.fruit.network.entity.user.ResponseRegister;
import com.bowl.fruit.network.entity.user.User;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by cathy on 2018/2/8.
 */

public interface FruitApi {

    @POST("/users/login")
    Observable<ResponseLogin> login(@Body User user);

    @POST("/users/register")
    Observable<ResponseRegister> register(@Body User user);

    @FormUrlEncoded
    @POST("/fruitList")
    Observable<ResponseFruits> getFruitList(@Field("type") int type, @Field("page") int page);

    @POST("/message")
    Observable<ResponseMessage> getMessageList(@Field("userType") int type, @Field("page") int page);

    @POST("/address")
    Observable<ResponseAddress> getAddressList(@Field("uid") String uid);

    @POST("/shopping")
    Observable<ResponseShopping> getShoppingList(@Field("uid") String uid, int page);

    @POST("/addShopping")
    Observable<BaseResponse> addShopping(@Body RequestAddShopping requestAddShopping);

    @POST("/deleteShopping")
    Observable<BaseResponse> deleteShopping(@Field("shoppingId") String id);


    @POST("/editAddress")
    Observable<BaseResponse> editAddress(@Body RequestAddress requestAddress);

    @POST("/addOrder")
    Observable<BaseResponse> addOrder(@Body Order order);

    @POST("/changeOrderStatus")
    Observable<BaseResponse> changeOrderStatus(@Field("orderId") String orderId, @Field("status") int status, @Field("deliverId") String deliverId);

    @POST("/orderNum")
    Observable<ResponseOrderNum> getOrderNum(@Field("uid") String userId);

    @POST("/orderList")
    Observable<ResponseOrders> getOrderList(@Field("type") int type, @Field("page") int page);

    @Multipart
    @POST("/upload")
    Observable<ResponseEditFruit> upload(@Part MultipartBody.Part image);

    @POST("/editFruit")
    Observable<BaseResponse> editFruit(@Body Fruit fruit);

}
