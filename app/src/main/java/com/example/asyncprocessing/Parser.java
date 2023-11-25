package com.example.asyncprocessing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parser extends AsyncTask<String, Void, List<ExchangeRate>> {

    private Context context;

    public Parser(Context context) {
        this.context = context;
    }

    @Override
    protected List<ExchangeRate> doInBackground(String... strings) {
        String xmlData = strings[0];
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));

            NodeList nodeList = document.getElementsByTagName("Cube");
            for (int i = 2; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String currencyCode = element.getAttribute("currency");
                String rate = element.getAttribute("rate");

                ExchangeRate exchangeRate = new ExchangeRate(currencyCode, rate);
                exchangeRates.add(exchangeRate);
            }
        } catch (Exception e) {
            Log.e("Parser", "Error parsing data: " + e.getMessage());
        }

        return exchangeRates;
    }

    @Override
    protected void onPostExecute(List<ExchangeRate> result) {
        super.onPostExecute(result);
        if (result != null && context instanceof MainActivity) {
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) context).refreshListView(result);
                }
            });
        }
    }
}
