package com.bowl.fruit.ui.buyer.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.mine.Address;
import com.bowl.fruit.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class AddressActivity extends BaseActivity {

    private ListView mListView;
    private AddressAdapter mAdapter;
    private RelativeLayout mSelect;
    private TextView mCancel, mEdit, mDelete;
    private int mSelectPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initTitle();
        initViews();
        initData();
    }

    private void initTitle(){
        ImageView mBack = findViewById(R.id.backBtn);
        TextView mTitle = findViewById(R.id.title);
        TextView mRight= findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.VISIBLE);

        mTitle.setText("收货地址");
        mRight.setText("添加");

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, AddressEditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        mSelect = findViewById(R.id.rl_select);
        mCancel = findViewById(R.id.tv_cancel);
        mEdit = findViewById(R.id.tv_edit);
        mDelete = findViewById(R.id.tv_delete);
        mListView = findViewById(R.id.lv_address);

        mAdapter = new AddressAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelect.setVisibility(View.VISIBLE);
                mSelectPosition = i;
                return true;
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
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
                Intent intent = new Intent(AddressActivity.this, AddressEditActivity.class);
                intent.putExtra("name", mAdapter.getItem(mSelectPosition).getName());
                intent.putExtra("phone", mAdapter.getItem(mSelectPosition).getPhone());
                intent.putExtra("address", mAdapter.getItem(mSelectPosition).getAddress());
                intent.putExtra("city", mAdapter.getItem(mSelectPosition).getCity());
                startActivity(intent);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelect.setVisibility(View.GONE);
            }
        });
    }

    private void initData(){
        Address address = new Address();
        address.setName("小碗");
        address.setPhone("13123456789");
        address.setAddress("广阳区荣华里小区");
        address.setCity("河北省廊坊市");

        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            addressList.add(address);
        }
        mAdapter.update(addressList);
    }
}
