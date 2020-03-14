package com.example.a2zbilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2zbilling.db.entities.ShopDetail;

import java.util.List;

public class ShopSettingActivity extends AppCompatActivity {
    private EditText shopName,ownerName,phoneNo,email,address;
    private ShopSettingActivityViewModel shopSettingActivityViewModel;
    private CardView cardViewSave;
    private int shopId;
    private ShopDetail shopDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_setting);
        setTitle("Shop Setting");
        shopName=findViewById(R.id.edit_shop_name);
        ownerName=findViewById(R.id.edit_onner_name);
        phoneNo=findViewById(R.id.edit_phone_no);
        email=findViewById(R.id.edit_email);
        address=findViewById(R.id.edit_address);
        shopSettingActivityViewModel = ViewModelProviders.of(this).get(ShopSettingActivityViewModel.class);
        shopSettingActivityViewModel.getAllShopDetail().observe(this, new Observer<List<ShopDetail>>() {
            @Override
            public void onChanged(List<ShopDetail> shopDetails) {
                shopDetail=shopDetails.get(0);
                shopId=shopDetails.get(0).getShopId();
            }
        });




        cardViewSave=findViewById(R.id.cardview_cheak);
        cardViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDetail shopDetail=new ShopDetail();
                    shopDetail.setShopName(shopName.getText().toString().trim());
                    shopDetail.setOwnerName(ownerName.getText().toString().trim());
                    shopDetail.setPhoneNo(phoneNo.getText().toString().trim());
                    shopDetail.setEmail(email.getText().toString().trim());
                    shopDetail.setAddress(address.getText().toString().trim());
                    shopSettingActivityViewModel.insertShopetail(shopDetail);
                    Toast.makeText(getBaseContext(),"shop Detail Add Sussesfully",Toast.LENGTH_SHORT).show();
                    finish();
            }
        });
    }
}
