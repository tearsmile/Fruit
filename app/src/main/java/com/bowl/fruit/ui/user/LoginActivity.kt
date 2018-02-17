package com.bowl.fruit.ui.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bowl.fruit.fruit.R
import com.bowl.fruit.ui.buyer.BuyerActivity
import com.bowl.fruit.ui.seller.SellerActivity
import kotlinx.android.synthetic.main.layout_login.*

/**
 * Created by cathy on 2018/2/9.
 */
class LoginActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        initViews()
    }

    private fun initViews(){
        btn_login.setOnClickListener {
            var intent = Intent()
            if(et_name.text.toString().equals("admin")) {
                intent.setClass(this@LoginActivity, SellerActivity::class.java)
            }else {
                intent.setClass(this@LoginActivity, BuyerActivity::class.java)
            }
            startActivity(intent)
        }
    }
}