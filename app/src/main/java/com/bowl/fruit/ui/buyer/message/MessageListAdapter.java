package com.bowl.fruit.ui.buyer.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class MessageListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Message> mData;

    public MessageListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<Message> messages){
        mData.clear();
        mData.addAll(messages);
        notifyDataSetChanged();
    }

    public void add(List<Message> messages){
//        mData.clear();
        mData.addAll(messages);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = mData.get(i);
        if(message.getType() == 0){
            viewHolder.mPic.setImageResource(R.drawable.icon_order);
        } else if (message.getType() == 1){
            viewHolder.mPic.setImageResource(R.drawable.icon_tips);
        } else if (message.getType() == 2){
            viewHolder.mPic.setImageResource(R.drawable.icon_discount);
        }
        viewHolder.mTitle.setText(message.getTitle());
        viewHolder.mDesc.setText(message.getDesc());
        return convertView;
    }

    private class ViewHolder{
        ImageView mPic;
        TextView mTitle,mDesc;

        ViewHolder(View view){
            mPic = view.findViewById(R.id.iv_type);
            mTitle = view.findViewById(R.id.tv_title);
            mDesc = view.findViewById(R.id.tv_desc);
        }
    }
}
