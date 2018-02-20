package com.bowl.fruit.ui.seller.enter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.message.Message;
import com.bowl.fruit.ui.buyer.message.MessageListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class MsgFragment extends Fragment{

    private XListView mListView;
    private MessageListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_message,null);
        initTitle(view);
        initViews(view);
        return view;
    }

    private void initTitle(View view){
        ImageView mBack = view.findViewById(R.id.backBtn);
        TextView mTitle = view.findViewById(R.id.title);
        TextView mRight= view.findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.VISIBLE);

        mTitle.setText("消息列表");
//        mRight.setText("发布");
    }

    private void initViews(View view){
        mListView = view.findViewById(R.id.lv_message);
        adapter = new MessageListAdapter(getActivity());
        mListView.setAdapter(adapter);

        Message message = new Message();
        message.setTitle("库存预警");
        message.setDesc("商品 智利蓝莓 库存不足");

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            messageList.add(message);
        }

        adapter.update(messageList);
    }
}
