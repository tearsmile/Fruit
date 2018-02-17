package com.bowl.fruit.ui.seller.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bowl.fruit.fruit.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsPictureAdapter extends RecyclerView.Adapter<GoodsPictureAdapter.PictureViewHolder> {

        private Context mContext;
        private List<String> mUrls;
//        private List<Integer> mData;

//    private int mWidth, mHeight;

//    private int mSelectedIndex;
    private OnItemClickListener mOnItemClick;

        public GoodsPictureAdapter(Context context) {
            mContext = context;
            mUrls =  new LinkedList<>();
//            mData = new ArrayList<>();
//        mHeight = DensityUtil.dip2px(mContext, 56);
//        mWidth = mHeight;
        }

        public void update(List<String> urls) {
//        mUrls = urls;
            mUrls.clear();
            mUrls.addAll(urls);
            mUrls.add("default");

            notifyDataSetChanged();
        }

        public void add(String url){
            mUrls.add(0,url);
            notifyDataSetChanged();
        }
//
//    public void select(int index) {
//        notifyItemChanged(index);
//        notifyItemChanged(mSelectedIndex);
//        mSelectedIndex = index;
//    }
//
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClick = listener;
    }

        @Override
        public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seller_pic,null));
        }

        @Override
        public void onBindViewHolder(final PictureViewHolder holder, final int position) {
//        loadImage(holder.imageView, mUrls.get(position));
            if(position == mUrls.size() - 1){
                holder.imageView.setImageResource(R.drawable.add);
            }else {
                loadImage(holder.imageView,mUrls.get(position));
//                holder.imageView.setImageResource(R.drawable.ic_back);
            }
//        if(position==mSelectedIndex) {
//            holder.mask.setSelected(true);
//        } else {
//            holder.mask.setSelected(false);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick!=null) {
                    mOnItemClick.onItemClick(v, holder.getAdapterPosition());
                }
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
//            imageView.setImageResource(R.drawable.ic_back);
            ImageLoader.getInstance().displayImage("file://" + url,imageView);

//        Observable.just(url)
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String s) {
//                        return EncryptedBitmapUtil.decryptDecodeBitmap(s, mWidth, mHeight);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Bitmap>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Bitmap bitmap) {
//                        imageView.setTag(url);
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
        }

        public class PictureViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
//        public View mask;

            public PictureViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
//            mask = itemView.findViewById(R.id.mask);
            }
        }

    public interface OnItemClickListener {
        void onItemClick(View view, int index);
    }
}

