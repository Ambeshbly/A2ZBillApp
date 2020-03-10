package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test extends AppCompatActivity {
    TextView textView;
    String strDate;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        DatePicker datePicker=findViewById(R.id.datePicker);
        textView=findViewById(R.id.date);
        Button bt=findViewById(R.id.bt);
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth() + 1;
//        int year = datePicker.getYear();

      //  SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
//        Date d = new Date(year, month, day);
//        strDate = dateFormatter.format(d);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar cal = Calendar.getInstance();
                year=cal.get(Calendar.YEAR);
                month=cal.get(Calendar.MONTH);
                dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(Test.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       // textView.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                        Date d = new Date(year-1900, month, dayOfMonth);
                        String selecteddate= DateFormat.getDateInstance().format(d);
                        textView.setText(selecteddate);
                    }},year,month,dayOfMonth);
                datePickerDialog.show();














//                Calendar calendar=Calendar.getInstance();
//
//
//
//                String selecteddate= DateFormat.getDateInstance().format( getFutureDate(calendar.getTime(),-4));
//
//                textView.setText(strDate);
            }
        });


    }

    public Date getFutureDate(Date currentDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);


        Date futureDate = cal.getTime();
        return futureDate;
    }

}
