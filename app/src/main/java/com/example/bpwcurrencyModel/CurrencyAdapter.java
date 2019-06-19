package com.example.bpwcurrencyModel;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bpwcurrency.R;

import java.util.Vector;

public class CurrencyAdapter extends BaseAdapter {
    Vector<Currency> currencyList;
    Context context;

    public CurrencyAdapter(Context context) {
        this.context = context;
        currencyList=new Vector<>();
    }

    public void addCurrency(Currency currCurrency){
        currencyList.add(currCurrency);
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_currency,parent,false);
        }
        Currency currCurrency = currencyList.get(position);
        TextView currencyName = convertView.findViewById(R.id.tvCurrencyName);
        currencyName.setText(currCurrency.getCurrency_name());

        TextView currencySell = convertView.findViewById(R.id.tvCurrencyBuy);
        currencySell.setText(""+currCurrency.getCurrency_value_buy());

        TextView currencyBuy=convertView.findViewById(R.id.tvCurrencySell);
        currencyBuy.setText(""+currCurrency.getCurrency_value_sell());

        return convertView;
    }
}
