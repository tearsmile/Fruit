package com.bowl.fruit.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by cathy on 2018/2/8.
 */

public class FruitNetService {
    private static final boolean MOCK_SERVICE = false;
    private static final String BASE_URL = "http://192.168.31.206:3000";

    private FruitApi mApi;

    public static FruitNetService getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        static FruitNetService INSTANCE = new FruitNetService();
    }

    private FruitNetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        if(MOCK_SERVICE) {
            MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                    .networkBehavior(NetworkBehavior.create())
                    .build();
            BehaviorDelegate<FruitApi> delegate = mockRetrofit.create(FruitApi.class);
            mApi = new MockFruitApi(delegate);
        } else {
            mApi = retrofit.create(FruitApi.class);
        }
    }

    public FruitApi getFruitApi(){
        return mApi;
    }
}
