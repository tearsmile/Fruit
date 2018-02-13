package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.fruit.ResponseFruits;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by cathy on 2018/2/13.
 */

public class FruitRepository {

    private FruitApi mApi;

    public static FruitRepository instance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final FruitRepository INSTANCE = new FruitRepository();
    }

    private FruitRepository() {
        mApi = FruitNetService.getInstance().getFruitApi();
    }

    public Observable<List<Fruit>> getList(int type, int page){
        return mApi.getFruitList(type, page)
                .map(new Func1<ResponseFruits, List<Fruit>>() {
                    @Override
                    public List<Fruit> call(ResponseFruits responseFruits) {
                        if(responseFruits.getCode() == 0){
                            return responseFruits.getFruitList();
                        }else {
                            return null;
                        }
                    }
                });
    }

}
