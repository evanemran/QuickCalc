package com.evanemran.calculator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.calculator.R;
import com.evanemran.calculator.adapters.UnitsRecyclerAdapter;
import com.evanemran.calculator.listeners.ClickListener;
import com.evanemran.calculator.models.UnitsMenu;

import java.util.ArrayList;
import java.util.List;

public class UnitConverterFragment extends Fragment {
    View view;
    RecyclerView recycler_units;
    UnitsRecyclerAdapter unitsRecyclerAdapter;
    List<UnitsMenu> unitsMenus = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_unit_converter, container, false);
        recycler_units = view.findViewById(R.id.recycler_units);

        unitsMenus.clear();
        setupUnitsRecycler();
        return view;
    }

    private void setupUnitsRecycler() {
        unitsMenus.add(UnitsMenu.LENGTH);
        unitsMenus.add(UnitsMenu.AREA);
        unitsMenus.add(UnitsMenu.VOLUME);
        unitsMenus.add(UnitsMenu.SPEED);
        unitsMenus.add(UnitsMenu.WEIGHT);
        unitsMenus.add(UnitsMenu.TEMPERATURE);
        unitsMenus.add(UnitsMenu.POWER);
        unitsMenus.add(UnitsMenu.PRESSURE);

        recycler_units.setHasFixedSize(true);
        recycler_units.setLayoutManager(new GridLayoutManager(getContext(), 3));
        unitsRecyclerAdapter = new UnitsRecyclerAdapter(getContext(), unitsMenus, clickListener);
        recycler_units.setAdapter(unitsRecyclerAdapter);
    }

    private final ClickListener<UnitsMenu> clickListener = new ClickListener<UnitsMenu>() {
        @Override
        public void onClicked(UnitsMenu object) {
            Toast.makeText(getContext(), object.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

}
