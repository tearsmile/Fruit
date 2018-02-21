package com.bowl.fruit.ui.seller.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bowl.fruit.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsPictureAdapter extends RecyclerView.Adapter<GoodsPictureAdapter.PictureViewHolder> {

    private Context mContext;
    private List<String> mUrls;

    private OnItemClickListener mOnItemClick;

    public GoodsPictureAdapter(Context context) {
        mContext = context;
        mUrls =  new LinkedList<>();
    }

    public void update(List<String> urls) {
        mUrls.clear();
        mUrls.addAll(urls);
        mUrls.add("default");

        notifyDataSetChanged();
    }

    public void add(String url){
        mUrls.add(0,url);
        notifyDataSetChanged();
    }

    public List<String> getData(){
        return mUrls;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClick = listener;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seller_pic,null));
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder holder, final int position) {
        if(position == mUrls.size() - 1){
            holder.imageView.setImageResource(R.drawable.add);
            holder.delete.setVisibility(View.GONE);
        }else {
            loadImage(holder.imageView,mUrls.get(position));
            holder.delete.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick!=null) {
                    mOnItemClick.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUrls.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUrls==null?0:mUrls.size();
    }

    private void loadImage(final ImageView imageView, final String url) {

        String tag = (String) imageView.getTag();
        if(!TextUtils.isEmpty(tag)&&tag.equals(url)) {
            return;
        }

        imageView.setTag(url);
        ImageLoader.getInstance().displayImage("file://" + url,imageView);

    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ImageView delete;

        public PictureViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int index);
    }
}

