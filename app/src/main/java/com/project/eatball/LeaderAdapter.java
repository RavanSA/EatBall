package com.project.eatball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.MyViewHolder> {


public List<LeaderInfo> leader_lists;
Context context;


public static class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView username, level;
    public ImageView profileImg;

    public MyViewHolder(View view) {
        super(view);
        username = view.findViewById(R.id.usernameleader);
        level  = view.findViewById(R.id.levelleader);
        profileImg = view.findViewById(R.id.profimg);
    }
}

    public LeaderAdapter(List<LeaderInfo> leader_lists, Context context) {
        this.leader_lists = leader_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LeaderInfo recycleList = leader_lists.get(position);
        holder.username.setText(recycleList.getUserName());
        holder.level.setText(recycleList.getLevel());
        Picasso.get().load(recycleList.getImglink()).into(holder.profileImg);

    }

    @Override
    public int getItemCount() {
        return leader_lists.size();
    }

}
