package com.bowl.fruit.ui.buyer.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.mine.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<Address> mData;

    public AddressAdapter(Context context){
        this.context = context;
        mData = new ArrayList<>();
    }

    public void update(List<Address> addressList){
        mData.clear();;
        mData.addAll(addressList);
        notifyDataSetChanged();
    }

    public void remove(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Address getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Address address = mData.get(position);
        viewHolder.name.setText(address.getName());
        viewHolder.phone.setText(address.getPhone());
        viewHolder.address.setText(address.getCity() + " " + address.getAddress());
        return convertView;
    }

    private class ViewHolder{
        TextView name, phone, address;

        ViewHolder(View view){
            name = view.findViewById(R.id.tv_name);
            phone = view.findViewById(R.id.tv_phone);
            address = view.findViewById(R.id.tv_address);
        }
    }

}
