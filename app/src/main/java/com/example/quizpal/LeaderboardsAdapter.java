package com.example.quizpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizpal.databinding.RowLeaderboardsBinding;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewholder>{

    Context context;
    ArrayList<User> users;

    public LeaderboardsAdapter(Context context, ArrayList<User> users) {
        this.context=context;
        this.users=users;


    }


    @NonNull
    @Override
    public LeaderboardViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_leaderboards,parent,false);
        return new LeaderboardViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewholder holder, int position) {
        User user=users.get(position);
        holder.binding.name.setText(user.getUsernameBox());
        holder.binding.coins.setText(String.valueOf(user.getCoins()));
        holder.binding.index.setText(String.format("%d",position+1));


        Glide.with(context)
                .load(user.getProfile())
                .into(holder.binding.profileImg);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewholder extends RecyclerView.ViewHolder {
        RowLeaderboardsBinding binding;

        public LeaderboardViewholder(@NonNull View itemView) {
            super(itemView);

            binding=RowLeaderboardsBinding.bind(itemView);
        }
    }
}
