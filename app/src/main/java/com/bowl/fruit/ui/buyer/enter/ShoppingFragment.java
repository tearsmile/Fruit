package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.ui.buyer.shopping.ShoppingDetailActivity;
import com.bowl.fruit.ui.buyer.shopping.ShoppingItem;
import com.bowl.fruit.ui.buyer.shopping.ShoppingListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/11.
 */

public class ShoppingFragment extends Fragment {

    private TextView mSettle;
    private XListView mListView;
    private ShoppingListAdapter mAdapter;
    private ImageView mSelectAll;

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
        mRight.setText("搜索");
    }

    private void initView(View view){
        mSettle = view.findViewById(R.id.tv_settle);
        mListView = view.findViewById(R.id.lv_shopping);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new ShoppingListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> urls = new ArrayList<>();
                double total = 0;
                double discount = 0;
                for (ShoppingItem item : mAdapter.getData()) {
                    if(item.isSelect()){
                        urls.add(item.getPic());
                        total += item.getPrice();
                        discount += item.getDiscount();
                    }
                }
                Intent intent = new Intent(getActivity(), ShoppingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("urls", urls);
                bundle.putDouble("total",total);
                bundle.putDouble("discount",discount);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        List<ShoppingItem> fruits = new ArrayList<>();
        ShoppingItem f = new ShoppingItem("智利蓝莓125g*1盒", "", 12.9, "这么好的蓝莓 都想留给你吃");
        for (int i = 0; i < 5; i++) {
            fruits.add(f);
        }
        mAdapter.update(fruits);

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
            }
        });
    }
}
