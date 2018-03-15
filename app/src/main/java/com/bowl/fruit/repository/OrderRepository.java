package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.order.Goods;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.network.entity.order.RequestAddOrder;
import com.bowl.fruit.network.entity.order.ResponseOrders;
import com.bowl.fruit.preference.PreferenceDao;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by CJ on 2018/2/20.
 */

public class OrderRepository {
    private FruitApi mApi;

    public static OrderRepository instance() {
        return OrderRepository.Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final OrderRepository INSTANCE = new OrderRepository();
    }

    private OrderRepository() {
        mApi = FruitNetService.getInstance().getFruitApi();
    }

    public Observable<List<Order>> getOrderList(int type, int page){
        return mApi.getOrderList(PreferenceDao.getInstance().getString("key_login_user_id",""),PreferenceDao.getInstance().getInt("key_login_type",0),type, page)
                .map(new Func1<ResponseOrders, List<Order>>() {
                    @Override
                    public List<Order> call(ResponseOrders responseOrders) {
                        if(responseOrders.getCode() == 0){
                            return responseOrders.getOrders();
                        }
                        return null;
                    }
                });
    }

    public Observable<BaseResponse> addOrder(List<Goods> goods, double price, double discount){
        Order order = new Order();
        order.setOrderId(System.currentTimeMillis()+"");
        order.setTimeStamp(System.currentTimeMillis());
        order.setPrice(price);
        order.setDiscount(discount);
        order.setGoods(goods);
        order.setStatus(0);
        RequestAddOrder requestAddOrder = new RequestAddOrder();
        requestAddOrder.setUid(PreferenceDao.getInstance().getString("key_login_user_id",""));
        requestAddOrder.setOrder(order);
        return mApi.addOrder(requestAddOrder);
    }
}
