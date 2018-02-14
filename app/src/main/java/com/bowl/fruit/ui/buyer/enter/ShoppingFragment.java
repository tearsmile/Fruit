package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.ui.buyer.fruit.FruitListAdapter;
import com.bowl.fruit.ui.buyer.shopping.ShoppingDetailActivity;
import com.bowl.fruit.ui.widget.XListView;

/**
 * Created by cathy on 2018/2/11.
 */

public class ShoppingFragment extends Fragment {

    private TextView mSettle;
    private XListView mListView;
    private FruitListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shopping,null);
        initView(view);
        return view;
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
        mAdapter = new FruitListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
