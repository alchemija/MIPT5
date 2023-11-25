package com.example.asyncprocessing;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView exchangeRateListView;
    private EditText currencyCodeEditText;
    private List<ExchangeRate> allExchangeRates;
    private List<ExchangeRate> filteredExchangeRates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exchangeRateListView = findViewById(R.id.exchangeRateListView);
        currencyCodeEditText = findViewById(R.id.currencyCodeEditText);
        allExchangeRates = new ArrayList<>();
        filteredExchangeRates = new ArrayList<>();

        ArrayAdapter<ExchangeRate> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, filteredExchangeRates);
        exchangeRateListView.setAdapter(adapter);

        DataLoader dataLoader = new DataLoader(MainActivity.this);
        dataLoader.execute();

        currencyCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterExchangeRates(editable.toString());
            }
        });
    }

    private void filterExchangeRates(String currencyCode) {
        filteredExchangeRates.clear();
        for (ExchangeRate rate : allExchangeRates) {
            if (rate.getCurrencyCode().toUpperCase().contains(currencyCode.toUpperCase())) {
                filteredExchangeRates.add(rate);
            }
        }
        ((ArrayAdapter) exchangeRateListView.getAdapter()).notifyDataSetChanged();
    }

    public void refreshListView(List<ExchangeRate> exchangeRates) {
        allExchangeRates.clear();
        allExchangeRates.addAll(exchangeRates);
        filteredExchangeRates.clear();
        filteredExchangeRates.addAll(exchangeRates);
        ((ArrayAdapter) exchangeRateListView.getAdapter()).notifyDataSetChanged();
    }
}
