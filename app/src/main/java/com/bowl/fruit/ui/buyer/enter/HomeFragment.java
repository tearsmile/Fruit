package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.ui.buyer.fruit.FruitDetailActivity;
import com.bowl.fruit.ui.buyer.fruit.FruitListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/11.
 */

public class HomeFragment extends Fragment {

    private XListView mListView;
    private FruitListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fruit_list,null);
        initViews(view);
        initData();
        return view;
    }

    private void initViews(View view){
        mListView = view.findViewById(R.id.lv_fruit);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FruitDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });

        mAdapter = new FruitListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }

    private void initData(){
        List<Fruit> fruits = new ArrayList<>();
        Fruit f = new Fruit("智利蓝莓125g*1盒", "", 12.9, "这么好的蓝莓 都想留给你吃");
        for (int i = 0; i < 10; i++) {
            fruits.add(f);
        }

        mAdapter.update(fruits);
    }
}
