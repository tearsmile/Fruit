package com.bowl.fruit.ui.buyer.enter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.repository.FruitRepository;
import com.bowl.fruit.ui.BaseFragment;
import com.bowl.fruit.ui.buyer.fruit.FruitDetailActivity;
import com.bowl.fruit.ui.buyer.fruit.FruitListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/11.
 */

public class HomeFragment extends BaseFragment {

    private XListView mListView;
    private FruitListAdapter mAdapter;
//    private RelativeLayout mKey;
//    private ImageView mSearch, mClose;
    private RelativeLayout rl_all,rl_hot,rl_discount,rl_recommend,rl_platter;
    private View line_all, line_hot, line_discount,line_recommend,line_platter;
    private int page = 1;
    private boolean hasNext = true;
    private int type = 0;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cycleCondition = -1;
            if (v.getId() == R.id.rl_all){
                cycleCondition = 0;
                line_all.setVisibility(View.VISIBLE);
                line_hot.setVisibility(View.GONE);
                line_discount.setVisibility(View.GONE);
                line_recommend.setVisibility(View.GONE);
                line_platter.setVisibility(View.GONE);

            } else if (v.getId() == R.id.rl_hot){
                cycleCondition = 1;
                line_all.setVisibility(View.GONE);
                line_hot.setVisibility(View.VISIBLE);
                line_discount.setVisibility(View.GONE);
                line_recommend.setVisibility(View.GONE);
                line_platter.setVisibility(View.GONE);
            } else if (v.getId() == R.id.rl_discount){
                cycleCondition = 2;
                line_all.setVisibility(View.GONE);
                line_hot.setVisibility(View.GONE);
                line_discount.setVisibility(View.VISIBLE);
                line_recommend.setVisibility(View.GONE);
                line_platter.setVisibility(View.GONE);
            } else if (v.getId() == R.id.rl_recommend){
                cycleCondition = 3;
                line_all.setVisibility(View.GONE);
                line_hot.setVisibility(View.GONE);
                line_discount.setVisibility(View.GONE);
                line_recommend.setVisibility(View.VISIBLE);
                line_platter.setVisibility(View.GONE);
            } else if (v.getId() == R.id.rl_platter){
                cycleCondition = 4;
                line_all.setVisibility(View.GONE);
                line_hot.setVisibility(View.GONE);
                line_discount.setVisibility(View.GONE);
                line_recommend.setVisibility(View.GONE);
                line_platter.setVisibility(View.VISIBLE);
            }
            if(cycleCondition == type){
                return;
            }
            type = cycleCondition;
//            request.setCycle_cond(cycleCondition);
            requestPage(type);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fruit_list,null);
        initTitle(view);
        initViews(view);
        return view;
    }

    private void initTitle(View view){
        ImageView mBack = view.findViewById(R.id.backBtn);
        TextView mTitle = view.findViewById(R.id.title);
        TextView mRight= view.findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.GONE);

        mTitle.setText("商品列表");
        mRight.setText("搜索");
//        mRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mKey.setVisibility(View.VISIBLE);
//            }
//        });
    }

    private void initViews(View view){

        rl_all = view.findViewById(R.id.rl_all);
        rl_hot = view.findViewById(R.id.rl_hot);
        rl_discount = view.findViewById(R.id.rl_discount);
        rl_recommend = view.findViewById(R.id.rl_recommend);
        rl_platter = view.findViewById(R.id.rl_platter);

        line_all = view.findViewById(R.id.line_all);
        line_hot = view.findViewById(R.id.line_hot);
        line_discount = view.findViewById(R.id.line_discount);
        line_recommend = view.findViewById(R.id.line_recommend);
        line_platter = view.findViewById(R.id.line_platter);

        rl_all.setOnClickListener(mOnClickListener);
        rl_hot.setOnClickListener(mOnClickListener);
        rl_discount.setOnClickListener(mOnClickListener);
        rl_recommend.setOnClickListener(mOnClickListener);
        rl_platter.setOnClickListener(mOnClickListener);

        mListView = view.findViewById(R.id.lv_fruit);

//        mSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestPage();
//            }
//        });
//        mClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mKey.setVisibility(View.GONE);
//            }
//        });
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                requestPage(type);
            }

            @Override
            public void onLoadMore() {
                requestNextPage(type);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FruitDetailActivity.class);
                intent.putExtra("fruit",mAdapter.getItem(position-1));
                getActivity().startActivity(intent);
            }
        });

        mAdapter = new FruitListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }

    private void resetListViewState() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setPullRefreshEnable(true);
        if(!hasNext){
            mListView.setPullLoadEnable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestPage(type);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestPage(type);
        }
    }

    private void requestPage(int type){
        page = 1;
        FruitRepository.instance().getList(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Fruit>>() {
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Fruit> fruits) {
                        mAdapter.update(fruits);
                        if(fruits.size() < 10){
                            hasNext = false;
                        }
                    }
                });
    }

    private void requestNextPage(int type){
        page++;
        FruitRepository.instance().getList(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Fruit>>() {
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Fruit> fruits) {
                        mAdapter.add(fruits);
                        if(fruits.size() < 10){
                            hasNext = false;
                        }
                    }
                });
    }
}
