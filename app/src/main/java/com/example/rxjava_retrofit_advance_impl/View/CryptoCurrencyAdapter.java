package com.example.rxjava_retrofit_advance_impl.View;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjava_retrofit_advance_impl.model.CryptoCurrency;
import com.example.rxjava_retrofit_advance_impl.R;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {
    private List<CryptoCurrency.Market> marketList;

    public CryptoCurrencyAdapter() {
        marketList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CryptoCurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_crypto_list,parent,false);
        return new CryptoCurrencyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyAdapter.ViewHolder holder, int position) {
        holder.txtCoin.setText(marketList.get(position).coinName);
        holder.txtMarket.setText(marketList.get(position).market);
        holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(marketList.get(position).price)));
        if(marketList.get(position).coinName.equalsIgnoreCase("eth")) {
            holder.cardView.setCardBackgroundColor(Color.BLUE);
        }else{
            holder.cardView.setCardBackgroundColor(Color.MAGENTA);
        }

    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public void setData(List<CryptoCurrency.Market> data){
        this.marketList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtCoin;
        public TextView txtPrice;
        public TextView txtMarket;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCoin = itemView.findViewById(R.id.txtCoin);
            txtMarket= itemView.findViewById(R.id.txtMarket);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
