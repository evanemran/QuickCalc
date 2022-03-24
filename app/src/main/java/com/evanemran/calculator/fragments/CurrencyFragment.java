package com.evanemran.calculator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evanemran.calculator.R;
import com.evanemran.calculator.listeners.ClickListener;
import com.evanemran.calculator.listeners.FetchDataListener;
import com.evanemran.calculator.manager.RequestManager;
import com.evanemran.calculator.models.Currencies;
import com.evanemran.calculator.models.CurrencyConverterResponse;
import com.evanemran.calculator.models.Symbols;
import com.evanemran.calculator.utils.CurrencyPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CurrencyFragment extends Fragment {
    View view;
    Button button;
    RequestManager manager;
    List<Currencies> currenciesList = new ArrayList<>();
    TextView textView_source_currency, textView_source_currency_name;
    TextView textView_target_currency, textView_target_currency_name;
    TextView textView_currency_output, textView_currency_input;
    TextView textView_last_updated;
    Currencies sourceCurrency, targetCurrency;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency, container, false);

        textView_source_currency = view.findViewById(R.id.textView_source_currency);
        textView_source_currency_name = view.findViewById(R.id.textView_source_currency_name);
        textView_target_currency = view.findViewById(R.id.textView_target_currency);
        textView_target_currency_name = view.findViewById(R.id.textView_target_currency_name);
        textView_currency_output = view.findViewById(R.id.textView_currency_output);
        textView_currency_input = view.findViewById(R.id.textView_currency_input);
        textView_last_updated = view.findViewById(R.id.textView_last_updated);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm a", Locale.ENGLISH);
        Date date = new Date();
        textView_last_updated.setText("Last Updated at " + dateFormat.format(date));

        manager = new RequestManager(getContext());

        sourceCurrency = new Currencies("BDT", "Bangladeshi Taka");
        targetCurrency = new Currencies("USD", "United States Dollar");

        Button button_ac = view.findViewById(R.id.button_ac);
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_currency_input.setText("");
            }
        });

        ImageButton button_clear = view.findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = textView_currency_input.getText().toString();
                if (!val.equals(""))
                {
                    val = val.substring(0, val.length() - 1);
                    textView_currency_input.setText(val);
                }
            }
        });

        Button button_equal = view.findViewById(R.id.button_equal);
        button_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView_currency_input.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Enter source amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                manager.ConvertCurrency(converterResponseFetchDataListener, sourceCurrency.getId(),
                        targetCurrency.getId(), textView_currency_input.getText().toString());
            }
        });

        Button button_dot = view.findViewById(R.id.button_dot);
        button_dot.setOnClickListener(dotClickListener);

        Button button_number0 = view.findViewById(R.id.button_number0);
        button_number0.setOnClickListener(numberClickListener);
        Button button_number1 = view.findViewById(R.id.button_number1);
        button_number1.setOnClickListener(numberClickListener);
        Button button_number2 = view.findViewById(R.id.button_number2);
        button_number2.setOnClickListener(numberClickListener);
        Button button_number3 = view.findViewById(R.id.button_number3);
        button_number3.setOnClickListener(numberClickListener);
        Button button_number4 = view.findViewById(R.id.button_number4);
        button_number4.setOnClickListener(numberClickListener);
        Button button_number5 = view.findViewById(R.id.button_number5);
        button_number5.setOnClickListener(numberClickListener);
        Button button_number6 = view.findViewById(R.id.button_number6);
        button_number6.setOnClickListener(numberClickListener);
        Button button_number7 = view.findViewById(R.id.button_number7);
        button_number7.setOnClickListener(numberClickListener);
        Button button_number8 = view.findViewById(R.id.button_number8);
        button_number8.setOnClickListener(numberClickListener);
        Button button_number9 = view.findViewById(R.id.button_number9);
        button_number9.setOnClickListener(numberClickListener);
        Button button_number00 = view.findViewById(R.id.button_number00);
        button_number00.setOnClickListener(numberClickListener);

        currenciesList = setupCurrencies();
        textView_source_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrencyPicker currencyPicker = new CurrencyPicker(currenciesList, sourceCurrenciesClickListener);
                currencyPicker.show(getActivity().getSupportFragmentManager(), "currencies");
            }
        });
        textView_target_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrencyPicker currencyPicker = new CurrencyPicker(currenciesList, targetCurrenciesClickListener);
                currencyPicker.show(getActivity().getSupportFragmentManager(), "currencies");
            }
        });
        return view;
    }

    private final Button.OnClickListener numberClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            textView_currency_input.setText(textView_currency_input.getText() + view.getTag().toString());
        }
    };

    private final View.OnClickListener dotClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (textView_currency_input.getText().toString().contains(".")){
                return;
            }
            else textView_currency_input.setText(textView_currency_input.getText().toString() + ".");
        }
    };

    private final FetchDataListener<CurrencyConverterResponse> converterResponseFetchDataListener = new FetchDataListener<CurrencyConverterResponse>() {
        @Override
        public void didFetch(CurrencyConverterResponse data, String message) {
            if (data.getError()>0){
                Toast.makeText(getContext(), data.getError_message(), Toast.LENGTH_SHORT).show();
            }
            textView_currency_output.setText(String.valueOf(data.getAmount()));
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

//    private final FetchDataListener<Symbols> allCurrenciesListener = new FetchDataListener<Symbols>() {
//        @Override
//        public void didFetch(Symbols data, String message) {
//            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void didError(String message) {
//            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//        }
//    };

    private List<Currencies> setupCurrencies() {
        List<Currencies> currencies = new ArrayList<>();
        currencies.add(new Currencies("BDT", "Bangladeshi Taka"));
        currencies.add(new Currencies("USD", "United States Dollar"));
        currencies.add(new Currencies("EUR", "Euro"));
        currencies.add(new Currencies("JPY", "Japanese Yen"));
        currencies.add(new Currencies("AED", "United Arab Emirates Dirham"));
        currencies.add(new Currencies("ARS", "Argentine Peso"));
        currencies.add(new Currencies("INR", "Indian Rupee"));
        currencies.add(new Currencies("PKR", "Pakistani Rupee"));
        currencies.add(new Currencies("QAR", "Qatari Rial"));
        currencies.add(new Currencies("SAR", "Saudi Riyal"));

        return currencies;
    }

    private final ClickListener<Currencies> sourceCurrenciesClickListener = new ClickListener<Currencies>() {
        @Override
        public void onClicked(Currencies object) {
            sourceCurrency = object;
            textView_source_currency.setText(sourceCurrency.getId());
            textView_source_currency_name.setText(sourceCurrency.getName());
        }
    };

    private final ClickListener<Currencies> targetCurrenciesClickListener = new ClickListener<Currencies>() {
        @Override
        public void onClicked(Currencies object) {
            targetCurrency = object;
            textView_target_currency.setText(targetCurrency.getId());
            textView_target_currency_name.setText(targetCurrency.getName());
        }
    };

}
