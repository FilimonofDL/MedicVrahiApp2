package com.medic.medicvrahiapp.a_chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medic.medicvrahiapp.model.ILoadMore;
import com.medic.medicvrahiapp.R;


public class ChatAdapter extends RecyclerView.Adapter<MyViewHolder> {

    boolean isLoading;
    private ILoadMore loadMore;
    int visibleThreshold = 5;
    int lastVisibleItem;
    int totalItemCount;
    RecyclerView recyclerViewAdaptere;

    public ChatAdapter( RecyclerView recyclerViews) {
        recyclerViewAdaptere=recyclerViews;


        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerViews.getLayoutManager();
        recyclerViews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    if (loadMore != null) {
                        loadMore.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
//        System.out.println("aray size "+Integer.toString(messages.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_chat_user, parent,false);
//System.out.println("Chat adapter INFL CHAT");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.tvTextChatUser.setText(messList.get(position).getTextMessage());
        holder.bind(ChatActivity.mesList.get(position));
    }
    public void setLoaded() {
        isLoading = false;
    }
    @Override
    public int getItemCount() {
        return ChatActivity.mesList.size();
    }

}
