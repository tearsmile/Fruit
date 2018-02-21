package com.bowl.fruit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bowl.fruit.R;
import com.bowl.fruit.preference.PreferenceDao;
import com.bowl.fruit.ui.user.LoginActivity;

/**
 * Created by CJ on 2018/2/19.
 */

public class AboutActivity extends BaseActivity {

    private static final String KEY_LOGIN = "key_login";
    private static final String KEY_LOGIN_TYPE = "key_login_type";
    private static final String KEY_LOGIN_USER_NAME = "key_login_user_name";
    private static final String KEY_LOGIN_USER_ID = "key_login_user_id";
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
    }

    private void initViews(){
        mLogout = findViewById(R.id.btn_logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, LoginActivity.class);
                PreferenceDao.getInstance().putBoolean(KEY_LOGIN,false);
                PreferenceDao.getInstance().putString(KEY_LOGIN_USER_NAME,"");
                PreferenceDao.getInstance().putString(KEY_LOGIN_USER_ID,"");
                PreferenceDao.getInstance().putInt(KEY_LOGIN_TYPE,-1);
                startActivity(intent);
                ActivityHelper.exitAll();
            }
        });
    }
}
