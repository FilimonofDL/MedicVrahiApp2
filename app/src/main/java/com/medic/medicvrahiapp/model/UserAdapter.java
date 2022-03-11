package com.medic.medicvrahiapp.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.a_chat.ChatActivity;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.TovarViewHolder> {
    List<User> userList;
    Activity activity;
    boolean isLoading;
    private ILoadMore loadMore;
    int visibleThreshold = 5;
    int lastVisibleItem;
    int totalItemCount;
    Class clickIntentItemClass;



    public UserAdapter(RecyclerView recyclerView, Activity activity,
                       List<User> vrahiList) {

        this.activity = activity;
        this.userList = vrahiList;
        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }


    @Override
    public TovarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_vrahi, parent, false);
        return new TovarViewHolder(view);


    }

    @Override
    public void onBindViewHolder(TovarViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }
    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setItems() {
        notifyDataSetChanged();
    }

    class TovarViewHolder extends RecyclerView.ViewHolder {
        TextView tvImiaVraha, tvOthestvoVraha, tvFamiliaVraha, tvSpecialnostVraha;
        ImageView ivFotoVraha;
        View view;



        public TovarViewHolder(View itemView) {
            super(itemView);

            tvImiaVraha = itemView.findViewById(R.id.tvImiaVraha);
            tvOthestvoVraha = itemView.findViewById(R.id.tvOthestvoVraha);
            tvFamiliaVraha = itemView.findViewById(R.id.tvFamiliaVraha);
            tvSpecialnostVraha = itemView.findViewById(R.id.tvSpecialnostVraha);
            ivFotoVraha=itemView.findViewById(R.id.ivFotoVraha);

            view=itemView;


        }

        public void bind(final User user) {



            tvImiaVraha.setText(user.getImia());
            tvOthestvoVraha.setText(user.getOthestvo());
            tvFamiliaVraha.setText(user.getFamilia());
//            tvSpecialnostVraha.setText(user.specialnost);
//            Picasso.get()
//                    .load(user.fotoVraha)
//                    .into(ivFotoVraha);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createZapisIntent(user);
                }
            });

        }
    }

    private void createZapisIntent(User user) {
        User.vibraniUser=user;
            Intent intent = new Intent(activity, ChatActivity.class);
            activity.startActivity(intent);
            activity.finish();


        System.out.println(User.vibraniUser.getGoogleUID()+"user vibran goog ID");
//        FirebaseHelper.saveVrahToFirebase(user);

    }
}