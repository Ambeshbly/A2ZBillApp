package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Stock;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;


public class ProfitAndLossActivity extends AppCompatActivity  {
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    private CardView cardViewCheak;
    private List<SaleDeatial> saleDeatialList;
    private List<Stock> stockList;
    private double itempurchasePriceTotal,itemSalePriceTotal;
    private AutoCompleteTextView autoCompleteTextViewStockName;
    private ProfitAndLossActivityViewModel profitAndLossActivityViewModel;
    private TextView textViewQuntityIn,textViewQuntityOut,textViewProfitAndLoss,textViewProfitAndLossText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_and_loss);
        barChart = findViewById(R.id.barChart);
        autoCompleteTextViewStockName =findViewById(R.id.edit_stock_Name);
        textViewQuntityIn=findViewById(R.id.textview_quentity_in);
        textViewQuntityOut=findViewById(R.id.textview_quentity_out);
        imageView=findViewById(R.id.exppersion);
        textViewProfitAndLoss=findViewById(R.id.textview_quentity_profit_or_loss1);
        textViewProfitAndLossText=findViewById(R.id.textview_quentity_profit_or_loss);
        textViewProfitAndLossText.setText("Profit & Loss");
        cardViewCheak=findViewById(R.id.cardview_cheak);
        getEntriesDefault();
        profitAndLossActivityViewModel = ViewModelProviders.of(this).get(ProfitAndLossActivityViewModel.class);
        stockList=new ArrayList<>();
        profitAndLossActivityViewModel.getAllItems().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                stockList.addAll(stocks);
            }
        });
        saleDeatialList=new ArrayList<>();
        profitAndLossActivityViewModel.getSalesDeatilBaseOnItemId1().observe(this, new Observer<List<SaleDeatial>>() {
            @Override
            public void onChanged(List<SaleDeatial> saleDeatials) {
                saleDeatialList.addAll(saleDeatials);
            }
        });
        ArrayAdapter<Stock> arrayAdapterForStockList=new ArrayAdapter<Stock>(this,android.R.layout.simple_list_item_1, stockList);
        autoCompleteTextViewStockName.setAdapter(arrayAdapterForStockList);
        autoCompleteTextViewStockName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double quntityIn=0,quntityOut=0;
                Stock selectedStock=(Stock) parent.getAdapter().getItem(position);
               for (int i = 0; i < saleDeatialList.size(); i++) {
                    SaleDeatial saleDeatial = saleDeatialList.get(i);
                    if(saleDeatial.getSaleDetailitemId()==selectedStock.getId()){
                        quntityOut=quntityOut+saleDeatial.getQuntity();
                    }
                }
               quntityIn=quntityOut+selectedStock.getPrimaryQuant();
               itempurchasePriceTotal=quntityOut*Double.parseDouble(selectedStock.getPurchasePerUnit());
               itemSalePriceTotal=quntityOut*Double.parseDouble(selectedStock.getSalePerUnit());
               textViewQuntityIn.setText("Qnty In :"+quntityIn);
               textViewQuntityOut.setText("Qnty Out :"+quntityOut);
            }
        });
        cardViewCheak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSalePriceTotal>itempurchasePriceTotal){
                    textViewProfitAndLoss.setText((itemSalePriceTotal-itempurchasePriceTotal)+" Rs");
                    textViewProfitAndLossText.setText("Profit");
                    imageView.setImageResource(R.drawable.smileyface);
                }else {
                    textViewProfitAndLoss.setText((itempurchasePriceTotal-itemSalePriceTotal)+" Rs");
                    textViewProfitAndLossText.setText("Loss");
                    imageView.setImageResource(R.drawable.sadface1);
                }
                getEntriesBaseOnItem();
                barDataSet = new BarDataSet(barEntries, "profite and loss");
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
            }
        });
    }
    public void getEntriesDefault() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, 1));
        barEntries.add(new BarEntry(2f, 1));
        barDataSet = new BarDataSet(barEntries, "profite and loss");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }
    public void getEntriesBaseOnItem() {
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, (float) itempurchasePriceTotal));
        barEntries.add(new BarEntry(2f, (float) itemSalePriceTotal));
        String[] xAxisLables = new String[]{"",""};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
    }
}

