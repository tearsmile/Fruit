package com.bowl.fruit.ui.buyer.enter;

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
import com.bowl.fruit.network.entity.order.Goods;
import com.bowl.fruit.network.entity.shopping.Shopping;
import com.bowl.fruit.preference.PreferenceDao;
import com.bowl.fruit.repository.ShoppingRepository;
import com.bowl.fruit.ui.buyer.shopping.ShoppingDetailActivity;
import com.bowl.fruit.ui.buyer.shopping.ShoppingListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/11.
 */

public class ShoppingFragment extends Fragment {

    private TextView mSettle;
    private XListView mListView;
    private ShoppingListAdapter mAdapter;
    private ImageView mSelectAll;
    private RelativeLayout mSelect;
    private TextView mCancel, mDelete, mSum;
    private int mSelectPosition = -1;
    private int page = 1;
    private boolean hasNext = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shopping,null);
        initTitle(view);
        initView(view);
        return view;
    }

    private void initTitle(View view){
        ImageView mBack = view.findViewById(R.id.backBtn);
        TextView mTitle = view.findViewById(R.id.title);
        TextView mRight= view.findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.VISIBLE);

        mTitle.setText("购物车");
    }

    private void initView(View view){
        mSelect = view.findViewById(R.id.rl_select);
        mCancel = view.findViewById(R.id.tv_cancel);
        mDelete = view.findViewById(R.id.tv_delete);
        mSettle = view.findViewById(R.id.tv_settle);
        mSum = view.findViewById(R.id.tv_price);

        mListView = view.findViewById(R.id.lv_shopping);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mAdapter = new ShoppingListAdapter(getActivity());
        mAdapter.setSelectChangeListener(new ShoppingListAdapter.SelectChangeListener() {
            @Override
            public void onSelectChange() {
                mSum.setText("￥"+mAdapter.getPrice());
            }
        });
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect.setVisibility(View.VISIBLE);
                mSelectPosition = i - 1;
                return true;
            }
        });
        mSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Goods> goods = new ArrayList<>();
                double total = 0;
                double discount = 0;
                for (Shopping item : mAdapter.getData()) {
                    if(item.isSelect()){
                        Goods g = new Goods();
                        g.setPic(item.getPic());
                        g.setNum(item.getNum());
                        g.setName(item.getName());
                        goods.add(g);
                        total += item.getPrice();
                        discount += item.getDiscount();
                    }
                }
                Intent intent = new Intent(getActivity(), ShoppingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", goods);
                bundle.putDouble("total",total);
                bundle.putDouble("discount",discount);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mSelectAll = view.findViewById(R.id.iv_select_all);
        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdapter.isSelectAll()) {
                    mSelectAll.setImageResource(R.drawable.icon_select);
                    mAdapter.unSelectAll();
                }else {
                    mSelectAll.setImageResource(R.drawable.icon_selected);
                    mAdapter.selectAll();
                }
                mSum.setText("￥"+mAdapter.getPrice());
            }
        });

        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
                mAdapter.remove(mSelectPosition);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });
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
        refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refresh();
        }
    }

    private void refresh(){
        page = 1;
        ShoppingRepository.instance().getShoppingList(PreferenceDao.getInstance().getString("key_login_user_id",""),page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shopping>>() {
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Shopping> shoppings) {
                        if(shoppings != null && shoppings.size() > 0){
                            if(shoppings.size() < 10){
                                hasNext = false;
                            }
                            mAdapter.update(shoppings);
                        }else {

                        }
                    }
                });
    }

    private void loadMore(){
        page++;
        ShoppingRepository.instance().getShoppingList(PreferenceDao.getInstance().getString("key_login_user_id",""),page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shopping>>() {
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Shopping> shoppings) {
                        if(shoppings != null && shoppings.size() >= 0){
                            if(shoppings.size() < 10){
                                hasNext = false;
                            }
                            mAdapter.add(shoppings);
                        }else {

                        }
                    }
                });
    }
}
