package com.bowl.fruit.ui.buyer.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.bowl.fruit.fruit.R;


/**
 * Created by frank on 17/5/10.
 */

public class RecyclerItemView extends RelativeLayout {

    public RecyclerItemView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_fruit_picture_review, this);
    }

}
