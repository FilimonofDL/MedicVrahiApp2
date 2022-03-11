package com.medic.medicvrahiapp.b_taloni;


import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medic.medicvrahiapp.model.ILoadMore;
import com.medic.medicvrahiapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class TalonLinInflateAdapter extends RecyclerView.Adapter<TalonLinInflateAdapter.TalonViewHolder> {
    List<List<TalonhikObj>> talonhikList;
    Activity activity;
    boolean isLoading;
    private ILoadMore loadMore;
    int visibleThreshold = 5;
    int lastVisibleItem;
    int totalItemCount;
    TalonhikAdapter talonhikAdapter;



    public TalonLinInflateAdapter(RecyclerView recyclerView, Activity activity,
                                  List<List<TalonhikObj>> talonhiks) {

        this.activity = activity;
        this.talonhikList = talonhiks;
        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//
//                    if (loadMore != null) {
//                        loadMore.onLoadMore();
//                    }
//                    isLoading = true;
//                }
//            }
//        });
    }


    @Override
    public TalonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_lin_lay_dlia_talonhikov, parent, false);

        return new TalonViewHolder(view);


    }

    @Override
    public void onBindViewHolder(TalonViewHolder holder, int position) {
        holder.bind(talonhikList.get(position));
    }
    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return talonhikList.size();
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setItems() {
        notifyDataSetChanged();
    }

    class TalonViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        View view;



        public TalonViewHolder(View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.linDliaTalonhikov);
            view=itemView;
        }

        public void bind(final List<TalonhikObj> talonhiks) {
            for(int i =0; i<talonhiks.size(); i++) {
                View child = activity.getLayoutInflater().inflate(R.layout.row_talonhik, null);
                TextView textView2 = child.findViewById(R.id.tvVremiaTalonhika);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                textView2.setText(simpleDateFormat.format(talonhiks.get(i).talonTime));
                linearLayout.addView(child);
            }
        }
    }


}