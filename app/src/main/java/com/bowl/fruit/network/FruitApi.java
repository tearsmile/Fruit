package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.fruit.ResponseEditFruit;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.message.ResponseMessage;
import com.bowl.fruit.network.entity.mine.Address;
import com.bowl.fruit.network.entity.mine.ResponseAddress;
import com.bowl.fruit.network.entity.order.RequestAddOrder;
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
    @POST("/fruit/list")
    Observable<ResponseFruits> getFruitList(@Field("type") int type, @Field("page") int page);

    @FormUrlEncoded
    @POST("/message/list")
    Observable<ResponseMessage> getMessageList(@Field("uid") String uid,@Field("userType") int type, @Field("page") int page);

    @FormUrlEncoded
    @POST("/shopping/list")
    Observable<ResponseShopping> getShoppingList(@Field("uid") String uid, @Field("page") int page);

    @POST("/shopping/add")
    Observable<BaseResponse> addShopping(@Body RequestAddShopping requestAddShopping);

    @FormUrlEncoded
    @POST("/shopping/delete")
    Observable<BaseResponse> deleteShopping(@Field("shoppingId") String id, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("/address/list")
    Observable<ResponseAddress> getAddressList(@Field("uid") String uid);

    @POST("/address/edit")
    Observable<BaseResponse> editAddress(@Body Address address);

    @FormUrlEncoded
    @POST("/address/delete")
    Observable<BaseResponse> deleteAddress(@Field("uid") String uid, @Field("id") String addressId);


    @POST("/order/add")
    Observable<BaseResponse> addOrder(@Body RequestAddOrder order);

    @FormUrlEncoded
    @POST("/order/update")
    Observable<BaseResponse> changeOrderStatus(@Field("uid") String uid, @Field("orderId") String orderId, @Field("status") int status, @Field("deliverId") String deliverId);

    @FormUrlEncoded
    @POST("/order/nums")
    Observable<ResponseOrderNum> getOrderNum(@Field("uid") String userId);

    @FormUrlEncoded
    @POST("/order/list")
    Observable<ResponseOrders> getOrderList(@Field("uid") String userId,@Field("uType")int uType, @Field("status") int type, @Field("page") int page);

    @Multipart
    @POST("/upload/img")
    Observable<ResponseEditFruit> upload(@Part MultipartBody.Part img);

    @POST("/fruit/edit")
    Observable<BaseResponse> editFruit(@Body Fruit fruit);

}
