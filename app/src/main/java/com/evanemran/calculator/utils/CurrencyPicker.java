package com.evanemran.calculator.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.R;
import com.evanemran.calculator.adapters.CurrencyAdapter;
import com.evanemran.calculator.listeners.ClickListener;
import com.evanemran.calculator.models.Currencies;
import com.evanemran.calculator.models.UnitsMenu;

import java.util.List;

public class CurrencyPicker extends DialogFragment implements ClickListener<Currencies> {
    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private final List<Currencies> currencies;
    private final ClickListener<Currencies> listener;
    Currencies selectedCurrency = new Currencies("BDT", "Bangladeshi Taka");

    public CurrencyPicker(List<Currencies> currencies, ClickListener<Currencies> listener) {
        this.currencies = currencies;
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CurrencyAdapter(getContext(), currencies, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_currencies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClicked(Currencies object) {
        selectedCurrency = object;
        listener.onClicked(object);
        dismiss();
    }
}
