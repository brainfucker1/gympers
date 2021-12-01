package com.example.gympers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gympers.R;
import com.example.gympers.TovarActivity;
import com.example.gympers.models.BrandsCategory;

import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder> {

    Context context;
    List<BrandsCategory> categoryList;

    public BrandsAdapter(Context context, List<BrandsCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brands_cat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.brandImg);
        holder.name.setText(categoryList.get(position).getName());
        holder.description.setText(categoryList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TovarActivity.class);
                intent.putExtra("detailed", categoryList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg;
        TextView name, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.brands_cat_img);
            name = itemView.findViewById(R.id.cat_brands_name);
            description = itemView.findViewById(R.id.cat_desc_name);
        }
    }
}
