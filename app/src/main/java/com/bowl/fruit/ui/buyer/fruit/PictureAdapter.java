package com.bowl.fruit.ui.buyer.fruit;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bowl.fruit.fruit.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class PictureAdapter extends PagerAdapter {

//    private List<String> mData = null;
    private List<Integer> mData = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;

    public PictureAdapter(Context context) {
        super();
        this.mContext = context ;
        this.mViewCache = new LinkedList<>();
    }

    public void update(List<Integer> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder viewHolder;
        View convertView;
        if(mViewCache.size() == 0){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fruit_pic,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_pic);
//            viewHolder.timeView = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(mData.get(position));
//        String text = getTimeStamp(mData.get(position));
//        viewHolder.timeView.setText(text);
//        loadImage(viewHolder.imageView, mData.get(position));

        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    public final class ViewHolder{
        public ImageView imageView;
    }
}
