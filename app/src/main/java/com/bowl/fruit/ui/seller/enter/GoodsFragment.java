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
import com.bowl.fruit.ui.seller.goods.GoodsEditActivity;
import com.bowl.fruit.ui.seller.goods.GoodsListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsFragment extends Fragment{

    private XListView mGoodsList;
    private GoodsListAdapter mAdapter;
    private RelativeLayout mSelect;
    private TextView mCancel, mEdit, mDelete;

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

                startActivity(intent);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });


        List<Fruit> fruits = new ArrayList<>();
        Fruit f = new Fruit("智利蓝莓125g*1盒", "", 12.9, "这么好的蓝莓 都想留给你吃");
        for (int i = 0; i < 5; i++) {
            fruits.add(f);
        }

        mAdapter.update(fruits);
        mGoodsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect.setVisibility(View.VISIBLE);
//                Intent intent = new Intent(getActivity(), GoodsEditActivity.class);
//                startActivity(intent);
                return true;
            }
        });
    }
}
