package com.example.rxjava_retrofit_advance_impl.data;

import com.example.rxjava_retrofit_advance_impl.model.CryptoCurrency;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptoCurrencyAPI {

    @GET("{coin}-usd")
    Observable<CryptoCurrency> getAllCryptoCurrency(@Path("coin") String coin);
}
