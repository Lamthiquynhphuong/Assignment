package edu.phuong.asm_restapi.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.phuong.asm_restapi.R;
import edu.phuong.asm_restapi.model.Product;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartList;

    public CardAdapter(Context context, List<Product> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("$ " + product.getPrice());
        Glide.with(context).load(product.getImage()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, priceTextView;
        ImageView productImageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
            productImageView = itemView.findViewById(R.id.productImg);
        }
    }
}

