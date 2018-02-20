package com.bowl.fruit.preference;

import android.content.Context;

/**
 * Created by keitacai on 15/8/10.
 */
public class ContextHolder {
    private ContextHolder(){}
    private static class InnerHodler{
        private final static ContextHolder sInstance = new ContextHolder();
    }
    private Context mContext;
    public Context getContext() {
        return mContext;
    }

    public static ContextHolder getInstance() {
        return InnerHodler.sInstance;
    }

    public void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context getAppContext(){
        return getInstance().getContext();
    }
}
