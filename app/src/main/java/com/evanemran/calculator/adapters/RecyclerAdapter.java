package com.evanemran.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.listeners.HistoryClickListener;
import com.evanemran.calculator.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    Context context;
    List<String> list;
    HistoryClickListener listener;

    public RecyclerAdapter(Context context, List<String> list, HistoryClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.textView_hist.setText(list.get(position));
        holder.textView_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RecyclerViewHolder extends RecyclerView.ViewHolder{
    TextView textView_hist;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_hist = itemView.findViewById(R.id.textView_hist);
    }
}
