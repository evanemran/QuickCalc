package com.evanemran.calculator.listeners;

public interface FetchDataListener<T> {
    void didFetch(T data, String message);
    void didError(String message);
}
