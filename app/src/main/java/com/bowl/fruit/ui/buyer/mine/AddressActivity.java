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
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.mine.ResponseAddress;
import com.bowl.fruit.ui.BaseActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                intent.putExtra("type",0);
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
                intent.putExtra("type",1);
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        FruitNetService.getInstance().getFruitApi()
                .getAddressList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSafeSubscriber<ResponseAddress>(){
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }

                    @Override
                    public void onNext(ResponseAddress responseAddress) {
                        super.onNext(responseAddress);
                        if(responseAddress.getCode() == 0) {
                            mAdapter.update(responseAddress.getAddressList());
                        }
                    }
                });

    }
}
