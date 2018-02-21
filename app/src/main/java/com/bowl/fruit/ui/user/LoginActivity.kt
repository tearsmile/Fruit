package com.bowl.fruit.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bowl.fruit.R
import com.bowl.fruit.network.FruitNetService
import com.bowl.fruit.network.entity.user.ResponseLogin
import com.bowl.fruit.network.entity.user.User
import com.bowl.fruit.preference.PreferenceDao
import com.bowl.fruit.ui.BaseActivity
import com.bowl.fruit.ui.buyer.BuyerActivity
import com.bowl.fruit.ui.seller.SellerActivity
import kotlinx.android.synthetic.main.activity_login.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by cathy on 2018/2/9.
 */
class LoginActivity: BaseActivity() {

    companion object {
        val KEY_LOGIN = "key_login"
        val KEY_LOGIN_TYPE = "key_login_type"
        val KEY_LOGIN_USER_NAME = "key_login_user_name"
        val KEY_LOGIN_USER_ID = "key_login_user_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initTitle()
        initViews()
        initData()
    }

    private fun initTitle() {
        val mBack = findViewById<ImageView>(R.id.backBtn)
        val mTitle = findViewById<TextView>(R.id.title)
        val mRight = findViewById<TextView>(R.id.rightBtn)

        mBack.visibility = View.GONE
        mRight.visibility = View.VISIBLE

        mTitle.text = "登录"
        mRight.text = "注册"

        mRight.setOnClickListener{
            var intent = Intent()
            intent.setClass(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews(){
        btn_login.setOnClickListener {
            FruitNetService.getInstance().fruitApi
                    .login(User(et_name.text.toString(),et_password.text.toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object: Subscriber<ResponseLogin>(){
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable?) {

                        }

                        override fun onNext(response: ResponseLogin) {
                            if(response.code == 0){
                                PreferenceDao.getInstance().putBoolean(KEY_LOGIN,true);
                                PreferenceDao.getInstance().putString(KEY_LOGIN_USER_NAME,et_name.text.toString());
                                PreferenceDao.getInstance().putString(KEY_LOGIN_USER_ID,response.uid);
                                var intent = Intent()
                                if(response.type == 1) {
                                    PreferenceDao.getInstance().putInt(KEY_LOGIN_TYPE,1)
                                    intent.setClass(this@LoginActivity, SellerActivity::class.java)
                                }else {
                                    PreferenceDao.getInstance().putInt(KEY_LOGIN_TYPE,0)
                                    intent.setClass(this@LoginActivity, BuyerActivity::class.java)
                                }
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this@LoginActivity,response.msg,Toast.LENGTH_LONG).show();
                            }
                        }
                    })

        }
    }

    private fun initData(){
        if(PreferenceDao.getInstance().getBoolean(KEY_LOGIN,false)){
            var intent = Intent()
            if(PreferenceDao.getInstance().getInt(KEY_LOGIN_TYPE,-1) == 1) {
                intent.setClass(this@LoginActivity, SellerActivity::class.java)
            }else {
                intent.setClass(this@LoginActivity, BuyerActivity::class.java)
            }
            startActivity(intent)
        }
    }
}