package com.bowl.fruit.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bowl.fruit.R
import com.bowl.fruit.network.FruitNetService
import com.bowl.fruit.network.entity.BaseResponse
import com.bowl.fruit.network.entity.user.User
import com.bowl.fruit.preference.PreferenceDao
import com.bowl.fruit.ui.BaseActivity
import com.bowl.fruit.ui.buyer.BuyerActivity
import kotlinx.android.synthetic.main.activity_register.*
import rx.Subscriber

/**
 * Created by CJ on 2018/2/20.
 */
class RegisterActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()
    }

    private fun initViews(){
        btn_register.setOnClickListener {
            if(et_password.text.toString().equals(et_password_confirm.text.toString())) {
                FruitNetService.getInstance().fruitApi
                        .register(User(et_name.text.toString(), et_password.text.toString()))
                        .subscribe(object : Subscriber<BaseResponse>() {
                            override fun onCompleted() {

                            }

                            override fun onError(e: Throwable?) {

                            }

                            override fun onNext(response: BaseResponse) {
                                if (response.code == 0) {
                                    PreferenceDao.getInstance().putBoolean(LoginActivity.KEY_LOGIN,true);
                                    var intent = Intent()
                                    intent.setClass(this@RegisterActivity, BuyerActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@RegisterActivity, response.msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
            }else{
                Toast.makeText(this@RegisterActivity, "密码不一致", Toast.LENGTH_LONG).show();
            }
        }
    }
}