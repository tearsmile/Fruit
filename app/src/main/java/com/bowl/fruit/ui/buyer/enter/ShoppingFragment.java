package com.bowl.fruit.ui.buyer.enter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bowl.fruit.fruit.R;

/**
 * Created by cathy on 2018/2/11.
 */

public class ShoppingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shopping,null);
        return view;
    }
}
