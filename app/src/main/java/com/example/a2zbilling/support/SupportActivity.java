package com.example.a2zbilling.support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a2zbilling.R;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }

    public void setSupport(View view) {
        switch (view.getId()){
            case R.id.customer_care_call_link:
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91 7827512583"));
                startActivity(intent);
                break;
            case R.id.customer_care_message_link:
                String contact = "+91 7827512583";
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.customer_care_email_link:
                Intent intent3 = new Intent(Intent.ACTION_SEND);
                intent3.setType("plain/text");
                intent3.putExtra(Intent.EXTRA_EMAIL, new String[] { "satendraly0001@gmail.com" });
                intent3.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent3.putExtra(Intent.EXTRA_TEXT, "mail body");
                startActivity(Intent.createChooser(intent3, ""));
                break;
            case R.id.customer_care_tutorial_link:
                Intent intent4=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                startActivity(intent4);
                break;
        }
    }
}
