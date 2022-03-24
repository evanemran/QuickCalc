package com.evanemran.calculator.manager;

import android.content.Context;

import com.evanemran.calculator.R;
import com.evanemran.calculator.listeners.FetchDataListener;
import com.evanemran.calculator.models.Currencies;
import com.evanemran.calculator.models.CurrenciesResponse;
import com.evanemran.calculator.models.CurrencyConverterResponse;
import com.evanemran.calculator.models.Symbols;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.amdoren.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void GetCurrencies(FetchDataListener<Symbols> listener){
        CallData callData = retrofit.create(CallData.class);
        Call<CurrenciesResponse> call = callData.callCurrencies(context.getString(R.string.api_key));
        call.enqueue(new Callback<CurrenciesResponse>() {
            @Override
            public void onResponse(Call<CurrenciesResponse> call, Response<CurrenciesResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().symbols, response.message());
            }

            @Override
            public void onFailure(Call<CurrenciesResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void ConvertCurrency(FetchDataListener<CurrencyConverterResponse> listener, String from, String to, String amount){
        CallData callData = retrofit.create(CallData.class);
        Call<CurrencyConverterResponse> call = callData.convertCurrency(context.getString(R.string.api_key), from, to, amount);
        call.enqueue(new Callback<CurrencyConverterResponse>() {
            @Override
            public void onResponse(Call<CurrencyConverterResponse> call, Response<CurrencyConverterResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<CurrencyConverterResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallData{
        @GET("symbols")
        Call<CurrenciesResponse> callCurrencies(
                @Query("access_key") String access_key
        );

        @GET("api/currency.php")
        Call<CurrencyConverterResponse> convertCurrency(
                @Query("api_key") String api_key,
                @Query("from") String from,
                @Query("to") String to,
                @Query("amount") String amount
        );
    }
}
