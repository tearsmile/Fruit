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
    private RelativeLayout mKey;
    private ImageView mSearch, mClose;
    private int page = 1;
    private boolean hasNext = true;

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
        mRight.setVisibility(View.VISIBLE);

        mTitle.setText("商品列表");
        mRight.setText("搜索");
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKey.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViews(View view){
        mKey = view.findViewById(R.id.rl_key);
        mSearch = view.findViewById(R.id.iv_search);
        mClose = view.findViewById(R.id.iv_close);
        mListView = view.findViewById(R.id.lv_fruit);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPage();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKey.setVisibility(View.GONE);
            }
        });
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                requestPage();
            }

            @Override
            public void onLoadMore() {
                requestNextPage();
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
        requestPage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            requestPage();
        }
    }

    private void requestPage(){
        page = 1;
        FruitRepository.instance().getList(0,page)
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
                        if(fruits.size() < 5){
                            hasNext = false;
                        }
                    }
                });
    }

    private void requestNextPage(){
        page++;
        FruitRepository.instance().getList(0,page)
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
                        if(fruits.size() < 5){
                            hasNext = false;
                        }
                    }
                });
    }
}
