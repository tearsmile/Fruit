package com.bowl.fruit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;

/**
 * Created by jsshang on 2016/8/12.
 * <p/>
 * 这个类是用来
 */
public class BaseActivity extends Activity {

    protected List<Subscriber<?>> mSubscribers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.add(this);
        mSubscribers = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.remove(this);
        unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityHelper.setmCurrentActivity(this);
    }

    private void addSubscriber(Subscriber<?> subscriber) {
        mSubscribers.add(subscriber);
    }

    private void removeSubscriber(Subscriber<?> subscriber) {
        if(mSubscribers==null||subscriber==null) {
            return;
        }
        if(!subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
        mSubscribers.remove(subscriber);
    }

    private void unsubscribe() {
        if(mSubscribers == null)
            return;
        for(Iterator<Subscriber<?>> iterator = mSubscribers.iterator(); iterator.hasNext(); ) {
            Subscriber<?> sub = iterator.next();
            if(sub!=null&&!sub.isUnsubscribed()) {
                sub.unsubscribe();
            }
            iterator.remove();
        }
    }

    public class BaseSafeSubscriber<T> extends Subscriber<T> {
        @CallSuper
        @Override
        public void onStart() {
            super.onStart();
            addSubscriber(this);
        }

        @CallSuper
        @Override
        public void onCompleted() {
            removeSubscriber(this);
        }

        @CallSuper
        @Override
        public void onError(Throwable throwable) {
            Log.d("A", "onError " + throwable);
            throwable.printStackTrace();
            removeSubscriber(this);
        }

        @Override
        public void onNext(T t) {

        }
    }

}
