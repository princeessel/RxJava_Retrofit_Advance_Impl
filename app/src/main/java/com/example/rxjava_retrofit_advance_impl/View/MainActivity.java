package com.example.rxjava_retrofit_advance_impl.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rxjava_retrofit_advance_impl.model.CryptoCurrency;
import com.example.rxjava_retrofit_advance_impl.R;
import com.example.rxjava_retrofit_advance_impl.data.CryptoCurrencyAPI;
import com.example.rxjava_retrofit_advance_impl.data.CryptoCurrencyService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CryptoCurrencyAdapter cryptoCurrencyAdapter;
    private CryptoCurrencyAPI cryptoCurrencyAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cryptoCurrencyAdapter= new CryptoCurrencyAdapter();
        recyclerView.setAdapter(cryptoCurrencyAdapter);

        fetchData();
    }



    private void fetchData() {
         Retrofit retrofit = CryptoCurrencyService.getCryptoCurrencyList();
         cryptoCurrencyAPI=retrofit.create(CryptoCurrencyAPI.class);


        Observable<List<CryptoCurrency.Market>> btcObservable = cryptoCurrencyAPI.getAllCryptoCurrency("btc")
                .map(result-> Observable.fromIterable(result.ticker.markets))
                .flatMap(x->x).filter(y->{y.coinName = "btc";
                return true;
                }).toList().toObservable();

        Observable<List<CryptoCurrency.Market>> ethObservable = cryptoCurrencyAPI.getAllCryptoCurrency("eth")
                .map(result-> Observable.fromIterable(result.ticker.markets))
                .flatMap(x->x).filter(y->{y.coinName = "eth";
                return true;
                }).toList().toObservable();

        Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(List<CryptoCurrency.Market> marketList) {
        if(marketList !=null && marketList.size() !=0){
            cryptoCurrencyAdapter.setData(marketList);

        }else {
            Toast.makeText(this, "NO RESULT FOUND",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
}
