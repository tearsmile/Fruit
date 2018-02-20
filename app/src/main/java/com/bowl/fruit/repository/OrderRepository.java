package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.network.entity.order.ResponseOrders;

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
        return mApi.getOrderList(type, page)
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
}
