package com.evanemran.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.R;
import com.evanemran.calculator.listeners.ClickListener;
import com.evanemran.calculator.models.Currencies;

import java.util.Currency;
import java.util.List;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder>{

    Context context;
    List<Currencies> list;
    ClickListener<Currencies> listener;

    public CurrencyAdapter(Context context, List<Currencies> list, ClickListener<Currencies> listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_currency, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.textView_currency.setText(list.get(position).getName() + " (" + list.get(position).getId() + ")");
        holder.card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class CurrencyViewHolder extends RecyclerView.ViewHolder{

    TextView textView_currency;
    CardView card_container;

    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_currency = itemView.findViewById(R.id.textView_currency);
        card_container = itemView.findViewById(R.id.card_container);
    }
}
