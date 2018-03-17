package com.bowl.fruit.ui.buyer.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.mine.Address;
import com.bowl.fruit.preference.PreferenceDao;
import com.bowl.fruit.ui.BaseActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CJ on 2018/2/19.
 */

public class AddressEditActivity extends BaseActivity {

    private EditText mName, mPhone, mCity, mAddress;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        initTitle();
        initViews();
    }

    private void initTitle(){
        ImageView mBack = findViewById(R.id.backBtn);
        TextView mTitle = findViewById(R.id.title);
        TextView mRight= findViewById(R.id.rightBtn);

        mBack.setVisibility(View.VISIBLE);
        mRight.setVisibility(View.GONE);

        mTitle.setText("编辑地址");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(){
        mName = findViewById(R.id.et_name);
        mPhone = findViewById(R.id.et_phone);
        mCity = findViewById(R.id.et_city);
        mAddress = findViewById(R.id.et_address);
        mSave = findViewById(R.id.btn_save);

        mName.setText(getIntent().getStringExtra("name"));
        mPhone.setText(getIntent().getStringExtra("phone"));
        mCity.setText(getIntent().getStringExtra("city"));
        mAddress.setText(getIntent().getStringExtra("address"));
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mName.getText().toString().equals("") || mPhone.getText().toString().equals("")
                        ||mCity.getText().toString().equals("")||mAddress.getText().toString().equals("")){
                    Toast.makeText(AddressEditActivity.this, "信息不完善",Toast.LENGTH_LONG).show();
                } else {
                    Address address = new Address();
                    address.setId(getIntent().getStringExtra("id"));
                    address.setName(mName.getText().toString());
                    address.setPhone(mPhone.getText().toString());
                    address.setCity(mCity.getText().toString());
                    address.setAddress(mAddress.getText().toString());
                    address.setUid(PreferenceDao.getInstance().getString("key_login_user_id",""));
//                    address.setType(getIntent().getIntExtra("type",-1));
                    FruitNetService.getInstance().getFruitApi()
                            .editAddress(address)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSafeSubscriber<BaseResponse>(){

                                @Override
                                public void onCompleted() {
                                    super.onCompleted();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    super.onError(throwable);
                                }

                                @Override
                                public void onNext(BaseResponse baseResponse) {
                                    super.onNext(baseResponse);
                                    if(baseResponse.getCode() == 0){
                                        Toast.makeText(AddressEditActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(AddressEditActivity.this, baseResponse.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
