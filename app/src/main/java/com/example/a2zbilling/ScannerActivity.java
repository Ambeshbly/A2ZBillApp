package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ZXingScannerView zXingScannerView;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView=new ZXingScannerView(this);
        setContentView(zXingScannerView);
    }

    @Override
    public void handleResult(Result rawResult) {
        //MainActivity.textView.setText(rawResult.getText());
//        AddUpdateStockActivity.textViewScanner.setText(rawResult.getText());
//        onBackPressed();

      Intent intent=new Intent();
        intent.putExtra("satva",rawResult.getText());
        setResult(RESULT_OK,intent);

      //  AddUpdateStockActivity.textViewScanner.setText(rawResult.getText());
      //  AddUpdateStockActivity.activityAddItemFloatingButtonBinding.textviewForScanner.setText(rawResult.getText());
        onBackPressed();


    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

        }
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

}
