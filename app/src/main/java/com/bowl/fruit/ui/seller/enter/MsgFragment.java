package com.bowl.fruit.ui.seller.enter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.message.Message;
import com.bowl.fruit.repository.MessageRepository;
import com.bowl.fruit.ui.buyer.message.MessageListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CJ on 2018/2/14.
 */

public class MsgFragment extends Fragment{

    private RelativeLayout mEmpty;
    private XListView mListView;
    private MessageListAdapter adapter;
    private int page = 1;
    private boolean hasNext = true;

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
        mEmpty = view.findViewById(R.id.rl_empty);
        mListView = view.findViewById(R.id.lv_message);
        adapter = new MessageListAdapter(getActivity());
        mListView.setAdapter(adapter);

        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    private void resetListViewState() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setPullRefreshEnable(true);
        if(!hasNext){
            mListView.setPullLoadEnable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            refresh();
        }
    }

    private void refresh(){
        page = 1;
        MessageRepository.instance().getMessage(1,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Message>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Message> messages) {
                        if(messages == null || messages.size() <= 0){
                            mEmpty.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);
                        }else {
                            mEmpty.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                            adapter.update(messages);
                            if(messages.size() < 10){
                                hasNext = false;
                            }
                            resetListViewState();
                        }
                    }
                });
    }

    private void loadMore(){
        page++;
        MessageRepository.instance().getMessage(1,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Message>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Message> messages) {
                        adapter.add(messages);
                        if(messages.size() < 10){
                            hasNext = false;
                        }
                        resetListViewState();
                    }
                });
    }
}
