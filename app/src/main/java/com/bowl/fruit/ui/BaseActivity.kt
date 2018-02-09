package com.bowl.fruit.ui

import android.app.Activity

/**
 * Created by cathy on 2018/2/9.
 */
abstract class BaseActivity:Activity() {
    abstract fun initIntent()
    abstract fun initViews()
    abstract fun initData()
}