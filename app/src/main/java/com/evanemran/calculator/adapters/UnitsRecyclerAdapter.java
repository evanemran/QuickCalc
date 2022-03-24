package com.evanemran.calculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.R;
import com.evanemran.calculator.listeners.ClickListener;
import com.evanemran.calculator.models.UnitsMenu;

import java.util.List;

public class UnitsRecyclerAdapter extends RecyclerView.Adapter<UnitsViewHolder>{

    Context context;
    List<UnitsMenu> list;
    ClickListener<UnitsMenu> listener;

    public UnitsRecyclerAdapter(Context context, List<UnitsMenu> list, ClickListener<UnitsMenu> listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UnitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UnitsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_units, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UnitsViewHolder holder, int position) {
        holder.textView_units.setText(list.get(position).getTitle());
        holder.imageView_units.setImageResource(list.get(position).getImage());
        holder.textView_units.setSelected(true);

        holder.cardView_units.setOnClickListener(new View.OnClickListener() {
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
class UnitsViewHolder extends RecyclerView.ViewHolder {
    CardView cardView_units;
    ImageView imageView_units;
    TextView textView_units;

    public UnitsViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView_units = itemView.findViewById(R.id.cardView_units);
        imageView_units = itemView.findViewById(R.id.imageView_units);
        textView_units = itemView.findViewById(R.id.textView_units);
    }
}
