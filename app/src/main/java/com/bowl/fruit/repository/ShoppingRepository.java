package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.network.entity.shopping.RequestAddShopping;
import com.bowl.fruit.network.entity.shopping.ResponseShopping;
import com.bowl.fruit.network.entity.shopping.Shopping;
import com.bowl.fruit.preference.PreferenceDao;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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

    public Observable<List<Shopping>> getShoppingList(String uid, int page){
        return FruitNetService.getInstance().getFruitApi()
                .getShoppingList(uid,page)
                .map(new Func1<ResponseShopping, List<Shopping>>() {
                    @Override
                    public List<Shopping> call(ResponseShopping responseShopping) {
                        if(responseShopping.getCode() == 0){
                            return responseShopping.getShoppingList();
                        }
                        return null;
                    }
                });
    }

    public Observable<BaseResponse> addShopping(Fruit fruit){
        RequestAddShopping requestAddShopping = new RequestAddShopping();
        requestAddShopping.setUid(PreferenceDao.getInstance().getString("key_login_user_id",""));
        requestAddShopping.setShopping(new Shopping(fruit));
        return mApi.addShopping(requestAddShopping);
    }



}
