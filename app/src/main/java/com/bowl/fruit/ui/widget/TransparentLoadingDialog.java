package com.bowl.fruit.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.bowl.fruit.R;

/**
 * Created by cachyjcao on 2017/3/7.
 */
public class TransparentLoadingDialog extends AlertDialog {

    TextView tv_msg;
    String msg = "任务加载中...";

    public TransparentLoadingDialog(Context context) {
        super(context);
    }

    public TransparentLoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.layout_common_loading);
        setCanceledOnTouchOutside(false);
        tv_msg = (TextView) findViewById(R.id.tv_reason);
        tv_msg.setText(msg);
    }

    public void setReason(String msg){
        this.msg = msg;
    }
}
