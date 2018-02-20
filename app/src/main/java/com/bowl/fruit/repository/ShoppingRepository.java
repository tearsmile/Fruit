package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;

/**
 * Created by CJ on 2018/2/20.
 */

public class ShoppingRepository {
    private FruitApi mApi;

    public static ShoppingRepository instance() {
        return ShoppingRepository.Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final ShoppingRepository INSTANCE = new ShoppingRepository();
    }

    private ShoppingRepository() {
        mApi = FruitNetService.getInstance().getFruitApi();
    }

//    public Observable<>

}
