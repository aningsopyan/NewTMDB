package com.example.aningsopyan.tmdbnewnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aningsopyan.tmdbnewnew.R;
import com.example.aningsopyan.tmdbnewnew.detail.DetailActivity;
import com.example.aningsopyan.tmdbnewnew.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{

    private List<Result> results;
    private Context context;
    private int rowLayout;
    private ArrayList<Result> mFilteredList;
    private List<Result> mArrayList;

    private String url_image = "http://image.tmdb.org/t/p/w185/";

    public static class Holder extends RecyclerView.ViewHolder{

        RelativeLayout linearLayout;
        TextView title,release,overview;
        ImageView image;

        public Holder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_item);
            title = itemView.findViewById(R.id.title);
            release = itemView.findViewById(R.id.release);
            overview = itemView.findViewById(R.id.overview);
            image = itemView.findViewById(R.id.image);
        }
    }

    public Adapter(List<Result> results,int rowLayout, Context context){
        this.mArrayList = new ArrayList<>();
        this.mArrayList.addAll(results);
        this.results = results;
        this.context = context;
        this.rowLayout = rowLayout;


    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        holder.title.setText(String.valueOf(results.get(position).getTitle()));
        holder.release.setText(String.valueOf(results.get(position).getReleaseDate()));
        holder.overview.setText(String.valueOf(results.get(position).getOverview()));

        Picasso.with(context)
                .load(url_image+results.get(position).getPosterPath())
                .resize(350,480)
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.valueOf(results.get(position).getId());
                Toast.makeText(context,String.valueOf(id),Toast.LENGTH_SHORT).show();
                Log.i("toast", String.valueOf(id));

                Intent intent = new Intent(context,DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id",Integer.valueOf(id));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        results.clear();

        if (charText.length() == 0){
            results.addAll(mArrayList);
        }
        else {
            for (Result wp : mArrayList){
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)){
                    results.add(wp);
                }
            }
        }

        notifyDataSetChanged();
    }
}
