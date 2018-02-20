package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.ui.AboutActivity;
import com.bowl.fruit.ui.buyer.mine.AddressActivity;
import com.bowl.fruit.ui.buyer.order.OrderListActivity;
import com.bowl.fruit.util.FastBlur;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/11.
 */

public class MineFragment extends Fragment {

    private ImageView mMineBg;
    private TextView mOrder;
    private RelativeLayout mAddress, mAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mine,null);
        initViews(view);
        return view;
    }



    private void initViews(View view){
        mMineBg = view.findViewById(R.id.iv_bg);
        mOrder = view.findViewById(R.id.tv_order_all);
        mAddress = view.findViewById(R.id.rl_address);
        mAbout = view.findViewById(R.id.rl_about);

        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
        Observable.just(R.mipmap.logo)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer id) {
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
                        return FastBlur.doBlur(bmp,12,false);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
//                        mMineBg.setImageBitmap(bitmap);
                        mMineBg.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });

    }
}
