package com.bowl.fruit.ui.seller.goods;

import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.fruit.ResponseEditFruit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by CJ on 2018/2/21.
 */

public class Uploader {
    private List<String> urls;
    private List<String> serverUrls;
    private int current = 0;

    public Uploader(List<String> urls){
        this.urls = urls;
        serverUrls = new ArrayList<>();
    }

    public List<String> getServerUrls(){
        return serverUrls;
    }

    public void startUpload() {
//        final CountDownLatch countDownLatch = new CountDownLatch(urls.size());
        for (String url : urls) {
//            upload(url);
//        }
//    }

//    public void upload(final String url){
            File file = new File(url);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            FruitNetService.getInstance().getFruitApi()
                    .upload(part)
                    .subscribe(new Subscriber<ResponseEditFruit>() {
                        @Override
                        public void onCompleted() {
//                            countDownLatch.countDown();
                        }

                        @Override
                        public void onError(Throwable e) {
//                            countDownLatch.countDown();
                        }

                        @Override
                        public void onNext(ResponseEditFruit baseResponse) {
                            if (baseResponse.getCode() == 0) {
                                serverUrls.add(baseResponse.getUrl());
                            }
                        }
                    });
        }

//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
