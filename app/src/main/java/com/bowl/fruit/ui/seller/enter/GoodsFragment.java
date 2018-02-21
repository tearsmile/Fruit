package com.bowl.fruit.ui.seller.enter;

import android.app.Fragment;
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
import com.bowl.fruit.ui.seller.goods.GoodsEditActivity;
import com.bowl.fruit.ui.seller.goods.GoodsListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsFragment extends Fragment{

    private XListView mGoodsList;
    private GoodsListAdapter mAdapter;
    private RelativeLayout mSelect;
    private TextView mCancel, mEdit, mDelete;
    private int mSelectPosition = -1;
    private int page = 1;
    private boolean hasNext = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_goods_list,null);
        initViews(view);
        initTitle(view);
        return view;
    }

    private void initTitle(View view){
        ImageView mBack = view.findViewById(R.id.backBtn);
        TextView mRight = view.findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.VISIBLE);

        mRight.setText("添加");
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoodsEditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(View view){
        mSelect = view.findViewById(R.id.rl_select);
        mCancel = view.findViewById(R.id.tv_cancel);
        mEdit = view.findViewById(R.id.tv_edit);
        mDelete = view.findViewById(R.id.tv_delete);


        mGoodsList = view.findViewById(R.id.lv_goods);
        mAdapter = new GoodsListAdapter(getActivity());
        mGoodsList.setAdapter(mAdapter);

        mGoodsList.setPullRefreshEnable(true);
        mGoodsList.setPullLoadEnable(true);
        mGoodsList.setAutoLoadEnable(false);
        mGoodsList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                requestPage();
            }

            @Override
            public void onLoadMore() {
                requestNextPage();
            }
        });

        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });
//        mDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mSelect.setVisibility(View.GONE);
//                mAdapter.remove(mSelectPosition);
//            }
//        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), GoodsEditActivity.class);
                intent.putExtra("fruit",mAdapter.getItem(mSelectPosition -1));
                startActivity(intent);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });

        mGoodsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect.setVisibility(View.VISIBLE);
                mSelectPosition = i;
                return true;
            }
        });
    }

    private void resetListViewState() {
        mGoodsList.stopRefresh();
        mGoodsList.stopLoadMore();
        mGoodsList.setPullRefreshEnable(true);
        if(!hasNext){
            mGoodsList.setPullLoadEnable(false);
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
