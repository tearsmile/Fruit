package com.bowl.fruit.ui.buyer

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.bowl.fruit.fruit.R
import com.bowl.fruit.ui.buyer.enter.HomeFragment
import com.bowl.fruit.ui.buyer.enter.MessageFragment
import com.bowl.fruit.ui.buyer.enter.MineFragment
import com.bowl.fruit.ui.buyer.enter.ShoppingFragment
import kotlinx.android.synthetic.main.activity_buyer.*

/**
 * Created by cathy on 2018/2/9.
 */
class BuyerActivity:Activity() {
    private var mCurrentFrag:Fragment? = null
    private var mHomeFragment:HomeFragment? = null
    private var mMessageFragment:MessageFragment? = null
    private var mShoppingFragment:ShoppingFragment? = null
    private var mMineFragment:MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer)
        initViews()
    }

    private fun initViews(){
        rl_home.setOnClickListener{
            switchToFrag(getOrCreateFragment(HomeFragment::class.java))
        }

        rl_shopping.setOnClickListener {
            switchToFrag(getOrCreateFragment(ShoppingFragment::class.java))
        }

        rl_message.setOnClickListener{
            switchToFrag(getOrCreateFragment(MessageFragment::class.java))
        }

        rl_mine.setOnClickListener {
            switchToFrag(getOrCreateFragment(MineFragment::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        initFrags()
        switchToFrag(getOrCreateFragment(HomeFragment::class.java))
    }

    private fun initFrags(){
        if(mCurrentFrag != null){
            return
        }
        var fragmentTransaction = fragmentManager.beginTransaction()
        mHomeFragment = getFragment(HomeFragment::class.java)
        mShoppingFragment = getFragment(ShoppingFragment::class.java)
        mMessageFragment = getFragment(MessageFragment::class.java)
        mMineFragment = getFragment(MineFragment::class.java)
        if(mHomeFragment != null ) fragmentTransaction.hide(mHomeFragment)
        if(mShoppingFragment != null ) fragmentTransaction.hide(mShoppingFragment)
        if(mMessageFragment != null ) fragmentTransaction.hide(mMessageFragment)
        if(mMineFragment != null ) fragmentTransaction.hide(mMineFragment)
        fragmentTransaction.commit()
    }

    private fun switchToFrag(fragment:Fragment?){
        if(mCurrentFrag == fragment || fragment == null){
            return
        }

        var fragmentTransaction = fragmentManager.beginTransaction()
        if(mCurrentFrag != null) {
            fragmentTransaction.hide(mCurrentFrag)
        }
        if(!fragment.isAdded){
            fragmentTransaction.add(R.id.frag_container,fragment,fragment.javaClass.name)
        } else {
            fragmentTransaction.show(fragment)
        }

        fragmentTransaction.commitAllowingStateLoss()
        mCurrentFrag = fragment
    }

    private fun <T> getOrCreateFragment(clazz: Class<T>): T? {
        var t: T? = fragmentManager.findFragmentByTag(clazz.name) as T
        if (t == null) {
            try {
                t = clazz.newInstance()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        return t
    }

    private fun <T> getFragment(clazz: Class<T>): T {
        return fragmentManager.findFragmentByTag(clazz.name) as T
    }
}