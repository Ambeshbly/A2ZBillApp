package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Test extends AppCompatActivity {

    List<String> list;
    private TestViewModel testViewModel;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    int year,month,dayOfMonth;
    private EditText from,to;
    Date f,t;
    private ImageView imageView;
    DatePickerDialog datePickerDialog;
    private TextView profitAndLossText,profitAndLoss,puchaseTotalTexTView,totalSaleTextView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("Profit And Loss");
        list = new ArrayList<String>();
        list.add("Today");
        list.add("yesterDay");
        list.add("Last Week");
        totalSaleTextView= findViewById(R.id.profit_and_loss_sale_total);
        puchaseTotalTexTView=findViewById(R.id.profit_and_loss_purchase_total);
        barChart = findViewById(R.id.barChart);
        imageView=findViewById(R.id.imageView_face);
        spinner=findViewById(R.id.select_duration_sppiner);
        profitAndLossText=findViewById(R.id.profit_and_loss_text);
        profitAndLoss=findViewById(R.id.profit_and_loss);
        CardView check=findViewById(R.id.cardview_cheak);
        getEntriesDefault();
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                switch (position) {
                    case 0://today
                       getToday();
                        break;
                    case 1://yesterday
                        getYesterDay();
                        break;
                    case 2://last week
                        getLastWeek();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEntriesBaseOnItem(2,2);
                barDataSet = new BarDataSet(barEntries, "profite and loss");
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
                Long from=DateConverter.fromDate(f);
                Long to=DateConverter.fromDate(t);
                List<Sales> sales=testViewModel.getSalesFromToFrom(from,to);
                double totalSales=0;
                double puchaseTotal=0;
                for(int i=0;i<sales.size();i++){
                  Sales sales1=sales.get(i);
                    List<SaleDeatial> saleDeatialList=testViewModel.getAllSAlesList(sales1.getSaleId());
                    for(int i1=0;i1<saleDeatialList.size();i1++){
                        puchaseTotal=puchaseTotal+Double.parseDouble(saleDeatialList.get(i1).getPurchasePrice());
                    }
                    totalSales=totalSales+Double.parseDouble(sales1.getTotalBillAmt());
                }
                totalSaleTextView.setText("Total sales: "+Double.toString(totalSales)+"\u20B9");
                puchaseTotalTexTView.setText("Total puchase: "+Double.toString(puchaseTotal)+"\u20B9");
                getEntriesBaseOnItem(puchaseTotal,totalSales);
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
        from=findViewById(R.id.fromEdit);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year=cal.get(Calendar.YEAR);
                month=cal.get(Calendar.MONTH);
                dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Test.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        f = new Date(year-1900, month, dayOfMonth);
                        Toast.makeText(getBaseContext(),"date inserted",Toast.LENGTH_SHORT).show();
                        from.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }},year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });
        to=findViewById(R.id.toEdit);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year=cal.get(Calendar.YEAR);
                month=cal.get(Calendar.MONTH);
                dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Test.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        t = new Date(year-1900, month, dayOfMonth);
                        Toast.makeText(getBaseContext(),"date inserted",Toast.LENGTH_SHORT).show();
                        to.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }},year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    public void getToday(){
        Calendar calendar=Calendar.getInstance();
        long LastDate= DateConverter.fromDate(calendar.getTime());
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long startDate=date.getTimeInMillis();
        List<Sales> todaySaleList=testViewModel.getSalesFromToFrom(startDate,LastDate);
        double totalSales=0;
        double puchaseTotal=0;
        for(int i=0;i<todaySaleList.size();i++){
            Sales sales=todaySaleList.get(i);
            List<SaleDeatial> saleDeatialList=testViewModel.getAllSAlesList(sales.getSaleId());
            for(int i1=0;i1<saleDeatialList.size();i1++){
                puchaseTotal=puchaseTotal+Double.parseDouble(saleDeatialList.get(i1).getPurchasePrice());
            }
            totalSales=totalSales+Double.parseDouble(sales.getTotalBillAmt());
        }
        totalSaleTextView.setText("Total sales: "+Double.toString(totalSales)+"\u20B9");
        puchaseTotalTexTView.setText("Total purchase: "+Double.toString(puchaseTotal)+"\u20B9");

        getEntriesBaseOnItem(puchaseTotal,totalSales);
        barDataSet = new BarDataSet(barEntries, "profite and loss");
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }

    public void getYesterDay(){
        Calendar calendar1=Calendar.getInstance();
        long LastDate1= DateConverter.fromDate(calendar1.getTime());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startDate1=cal.getTimeInMillis();
        List<Sales> todaySaleList1=testViewModel.getSalesFromToFrom(startDate1,LastDate1);
        double totalSales1=0;
        double purchaseTotal=0;
        for(int i=0;i<todaySaleList1.size();i++){
            Sales sales=todaySaleList1.get(i);
            List<SaleDeatial> saleDeatialList=testViewModel.getAllSAlesList(sales.getSaleId());
            for(int i1=0;i1<saleDeatialList.size();i1++){
                purchaseTotal=purchaseTotal+Double.parseDouble(saleDeatialList.get(i1).getPurchasePrice());
            }
            totalSales1=totalSales1+Double.parseDouble(sales.getTotalBillAmt());
        }
        totalSaleTextView.setText("Total sales: "+Double.toString(totalSales1)+"\u20B9");
        puchaseTotalTexTView.setText("Total puchase: "+Double.toString(purchaseTotal)+"\u20B9");
        getEntriesBaseOnItem(purchaseTotal,totalSales1);
        barDataSet = new BarDataSet(barEntries, "profite and loss");
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }

    public void getLastWeek(){
        Calendar calendar2=Calendar.getInstance();
        long LastDate2= DateConverter.fromDate(calendar2.getTime());
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -7);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        long startDate2=cal1.getTimeInMillis();

        List<Sales> todaySaleList2=testViewModel.getSalesFromToFrom(startDate2,LastDate2);
        double totalSales2=0;
        double purchaseTotal1=0;
        for(int i=0;i<todaySaleList2.size();i++){
            Sales sales=todaySaleList2.get(i);
            List<SaleDeatial> saleDeatialList=testViewModel.getAllSAlesList(sales.getSaleId());
            for(int i1=0;i1<saleDeatialList.size();i1++){
                purchaseTotal1=purchaseTotal1+Double.parseDouble(saleDeatialList.get(i1).getPurchasePrice());
            }
            totalSales2=totalSales2+Double.parseDouble(sales.getTotalBillAmt());
        }
        totalSaleTextView.setText("Total sales: "+Double.toString(totalSales2)+"\u20B9");
        puchaseTotalTexTView.setText("Total purchase: "+Double.toString(purchaseTotal1)+"\u20B9");
        getEntriesBaseOnItem(purchaseTotal1,totalSales2);
        barDataSet = new BarDataSet(barEntries, "profite and loss");
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
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

    public void getEntriesBaseOnItem(double totalPurchase,double totalSale) {
        if(totalSale>totalPurchase){
            profitAndLoss.setText((totalSale-totalPurchase)+" Rs");
            profitAndLossText.setText("Profit");
            imageView.setImageResource(R.drawable.smileyface);
        }else {
            profitAndLoss.setText((totalPurchase-totalSale)+" Rs");
            profitAndLossText.setText("Loss");
            imageView.setImageResource(R.drawable.sadface1);
        }

        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, (float) totalPurchase));
        barEntries.add(new BarEntry(2f, (float) totalSale));
        String[] xAxisLables = new String[]{"",""};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_profit_and_loss, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.byItem:
                Intent intent=new Intent(getBaseContext(),ProfitAndLossActivity.class);
                startActivity(intent);
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }




}
