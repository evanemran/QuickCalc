package com.evanemran.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.evanemran.calculator.adapters.ViewPagerAdapter;
import com.evanemran.calculator.fragments.CalculatorFragment;
import com.evanemran.calculator.fragments.CurrencyFragment;
import com.evanemran.calculator.fragments.UnitConverterFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public TextView textView_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        CalculatorFragment calculatorFragment = new CalculatorFragment();
        CurrencyFragment currencyFragment = new CurrencyFragment();
        UnitConverterFragment unitConverterFragment = new UnitConverterFragment();
        viewPagerAdapter.addFragment(calculatorFragment, "Calculator");
        viewPagerAdapter.addFragment(currencyFragment, "Exchange Rate");
        viewPagerAdapter.addFragment(unitConverterFragment, "Unit Converter");
        viewPager.setAdapter(viewPagerAdapter);
    }
}