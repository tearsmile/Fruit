package com.bowl.fruit.ui.seller

import android.app.Fragment
import android.os.Bundle
import com.bowl.fruit.fruit.R
import com.bowl.fruit.ui.BaseActivity
import com.bowl.fruit.ui.seller.enter.GoodsFragment
import com.bowl.fruit.ui.seller.enter.MsgFragment
import com.bowl.fruit.ui.seller.enter.OrderFragment
import kotlinx.android.synthetic.main.activity_seller.*

/**
 * Created by cathy on 2018/2/9.
 */
class SellerActivity:BaseActivity() {
    private var mCurrentFrag: Fragment? = null
    private var mGoodsFragment:GoodsFragment? = null
    private var mMessageFragment: MsgFragment? = null
    private var mOrderFragment:OrderFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)
        initViews()
    }

    private fun initViews(){
        rl_goods.setOnClickListener{
            switchToFrag(getOrCreateFragment(GoodsFragment::class.java))
        }

//        rl_msg.setOnClickListener{
//            switchToFrag(getOrCreateFragment(MsgFragment::class.java))
//        }
//
//        rl_order.setOnClickListener {
//            switchToFrag(getOrCreateFragment(MineFragment::class.java))
//        }
    }

    override fun onResume() {
        super.onResume()
        initFrags()
    }

    private fun initFrags(){
        if(mCurrentFrag != null){
            return
        }
        var fragmentTransaction = fragmentManager.beginTransaction()
        mGoodsFragment = getFragment(GoodsFragment::class.java)
//        mShoppingFragment = getFragment(ShoppingFragment::class.java)
//        mMessageFragment = getFragment(com.bowl.fruit.ui.buyer.enter.MsgFragment::class.java)
//        mMineFragment = getFragment(MineFragment::class.java)
        if(mGoodsFragment != null ) fragmentTransaction.hide(mGoodsFragment)
//        if(mShoppingFragment != null ) fragmentTransaction.hide(mShoppingFragment)
//        if(mMessageFragment != null ) fragmentTransaction.hide(mMessageFragment)
//        if(mMineFragment != null ) fragmentTransaction.hide(mMineFragment)
        fragmentTransaction.commit()
        switchToFrag(getOrCreateFragment(GoodsFragment::class.java))
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