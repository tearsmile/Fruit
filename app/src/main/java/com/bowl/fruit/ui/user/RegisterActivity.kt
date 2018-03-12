package com.bowl.fruit.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bowl.fruit.R
import com.bowl.fruit.network.FruitNetService
import com.bowl.fruit.network.entity.user.ResponseRegister
import com.bowl.fruit.network.entity.user.User
import com.bowl.fruit.preference.PreferenceDao
import com.bowl.fruit.ui.BaseActivity
import com.bowl.fruit.ui.buyer.BuyerActivity
import kotlinx.android.synthetic.main.activity_register.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<ResponseRegister>() {
                            override fun onCompleted() {

                            }

                            override fun onError(e: Throwable?) {
                                Log.d("cathy",e.toString())
                            }

                            override fun onNext(response: ResponseRegister) {
                                Log.d("cathy","register response code:"+response.code+";msg:"+response.msg+";id:"+response.id)
                                if (response.code == 0) {
                                    PreferenceDao.getInstance().putBoolean(LoginActivity.KEY_LOGIN,true);
                                    PreferenceDao.getInstance().putString(LoginActivity.KEY_LOGIN_USER_ID,response.id);
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