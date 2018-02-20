package com.bowl.fruit.network;

import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.fruit.FruitDetailResponse;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;
import com.bowl.fruit.network.entity.message.Message;
import com.bowl.fruit.network.entity.message.ResponseMessage;
import com.bowl.fruit.network.entity.mine.Address;
import com.bowl.fruit.network.entity.mine.RequestAddress;
import com.bowl.fruit.network.entity.mine.ResponseAddress;
import com.bowl.fruit.network.entity.order.Order;
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
    public Observable<ResponseMessage> getMessageList(int type, int page) {
        ResponseMessage responseMessage = new ResponseMessage();
        Message message = new Message();
        message.setTitle("物流信息");
        message.setDesc("商品 智利蓝莓 已发货");

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            messageList.add(message);
        }

        responseMessage.setMessages(messageList);
        return delegate.returningResponse(responseMessage).getMessageList(type, page);
    }

    @Override
    public Observable<ResponseAddress> getAddressList(String uid) {
        ResponseAddress responseAddress = new ResponseAddress();
        Address address = new Address();
        address.setName("小碗");
        address.setPhone("13123456789");
        address.setAddress("广阳区荣华里小区");
        address.setCity("河北省廊坊市");

        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            addressList.add(address);
        }
        responseAddress.setAddressList(addressList);
        return delegate.returningResponse(responseAddress).getAddressList(uid);
    }

    @Override
    public Observable<BaseResponse> editAddress(RequestAddress requestAddress) {
        return delegate.returningResponse(new BaseResponse()).editAddress(requestAddress);
    }

    @Override
    public Observable<ResponseOrderNum> getOrderNum(int userId) {
        return null;
    }

    @Override
    public Observable<ResponseOrders> getOrderList(int type, int page) {
        ResponseOrders responseOrders = new ResponseOrders();
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        for (int i = 0; i < 5; i++) {
            orders.add(order);
        }
        responseOrders.setOrders(orders);
        return delegate.returningResponse(responseOrders).getOrderList(type,page);
    }
}
