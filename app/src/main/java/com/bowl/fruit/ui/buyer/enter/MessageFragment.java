package com.bowl.fruit.ui.buyer.enter;

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
 * Created by cathy on 2018/2/11.
 */

public class MessageFragment extends Fragment {

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
    }

    private void initViews(View view){
        mListView = view.findViewById(R.id.lv_message);
        adapter = new MessageListAdapter(getActivity());
        mListView.setAdapter(adapter);

        Message message = new Message();
        message.setTitle("物流信息");
        message.setDesc("商品 智利蓝莓 已发货");

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            messageList.add(message);
        }

        adapter.update(messageList);
    }
}
