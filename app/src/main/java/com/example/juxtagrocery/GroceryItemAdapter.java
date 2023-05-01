package com.example.juxtagrocery;

import static com.example.juxtagrocery.GroceryItemActivity.GROCERY_ITEM_KEY;

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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder>{
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                navigate grocery items
                Intent intent = new Intent(context, GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY, items.get(position));
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageurl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView parent;
        private TextView txtName,txtPrice;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.image);
        }
    }
}
