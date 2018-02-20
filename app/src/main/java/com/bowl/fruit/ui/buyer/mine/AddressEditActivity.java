package com.bowl.fruit.ui.buyer.mine;

import android.os.Bundle;
import android.widget.EditText;

import com.bowl.fruit.R;
import com.bowl.fruit.ui.BaseActivity;

/**
 * Created by CJ on 2018/2/19.
 */

public class AddressEditActivity extends BaseActivity {

    private EditText mName, mPhone, mCity, mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        initViews();
    }

    private void initViews(){
        mName = findViewById(R.id.et_name);
        mPhone = findViewById(R.id.et_phone);
        mCity = findViewById(R.id.et_city);
        mAddress = findViewById(R.id.et_address);

        mName.setText(getIntent().getStringExtra("name"));
        mPhone.setText(getIntent().getStringExtra("phone"));
        mCity.setText(getIntent().getStringExtra("city"));
        mAddress.setText(getIntent().getStringExtra("address"));
    }
}
