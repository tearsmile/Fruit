package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.order.ResponseOrderNum;
import com.bowl.fruit.preference.PreferenceDao;
import com.bowl.fruit.ui.AboutActivity;
import com.bowl.fruit.ui.buyer.mine.AddressActivity;
import com.bowl.fruit.ui.buyer.order.OrderListActivity;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/11.
 */

public class MineFragment extends Fragment {

    private ImageView mMineBg;
    private TextView mOrderAll, mUserName;
    private RelativeLayout mAddress, mAbout;
    private RelativeLayout mOrder, mDeliver, mFinish;
    private TextView mOrderedNum, mDeliverNum, mFinishNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mine,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mOrder = view.findViewById(R.id.rl_order_ready);
        mDeliver = view.findViewById(R.id.rl_deliver);
        mFinish = view.findViewById(R.id.rl_finish);

        mOrderedNum = view.findViewById(R.id.tv_ordered);
        mDeliverNum = view.findViewById(R.id.tv_deliver);
        mFinishNum = view.findViewById(R.id.tv_finish);

        mMineBg = view.findViewById(R.id.iv_bg);
        mUserName = view.findViewById(R.id.tv_user_name);
        mOrderAll = view.findViewById(R.id.tv_order_all);
        mAddress = view.findViewById(R.id.rl_address);
        mAbout = view.findViewById(R.id.rl_about);

        mUserName.setText(PreferenceDao.getInstance().getString("key_login_user_name",""));
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

        mOrderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });

        mDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
//        Observable.just(R.mipmap.logo)
//                .map(new Func1<Integer, Bitmap>() {
//                    @Override
//                    public Bitmap call(Integer id) {
//                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
//                        return FastBlur.doBlur(bmp,12,false);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Bitmap>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Bitmap bitmap) {
////                        mMineBg.setImageBitmap(bitmap);
//                        mMineBg.setBackgroundColor(getResources().getColor(R.color.white));
//                    }
//                });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshOrderNum();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refreshOrderNum();
        }
    }

    private void refreshOrderNum(){
        FruitNetService.getInstance().getFruitApi()
                .getOrderNum(PreferenceDao.getInstance().getString("key_login_user_id",""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseOrderNum>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseOrderNum responseOrderNum) {
                        if(responseOrderNum.getCode() == 0){
                            List<Integer> nums = responseOrderNum.getNums();
                            mOrderedNum.setText("已下单("+nums.get(0)+")");
                            mDeliverNum.setText("配送中("+nums.get(1)+")");
                            mFinishNum.setText("已完成("+nums.get(2)+")");
                        }
                    }
                });
    }
}
