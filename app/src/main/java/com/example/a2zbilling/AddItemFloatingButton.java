package com.example.a2zbilling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddItemFloatingButton extends AppCompatActivity {

    //EditText declaration
    private TextInputLayout textinputitemname,textinputitemquantiity,textinputitempurchaseperunit,textinputitempurchasetotal,textinputitemsaleunit,textinputitemsaletotal;

    //final permisssion code variable declartion which is used to take permission of the camra to the user
    private static final int PERMISSION_CODE=1000;

    //final image capture code variable declartion which is used to take image to the camra
    private static final int IMAGE_CAPTURE_CODE=1001;

    //image View delcartion
    private ImageView imageViewForItem;

    //declation of textview for show id of the items
    private TextView textViewforid;

    //declartion of unit button for show all unit to the user in the form on dialog fragmnets
    private Button unit_Button;

    //declation of cardview which is used to send the default items activity
    private CardView defualtitemListCardView;

    //image path uri
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_floating_button);

        //finding the item name edit text in the Xml file
        textinputitemname=findViewById(R.id.text_input_itemname);

        //finding the item quentity edit text in the Xml file
        textinputitemquantiity=findViewById(R.id.text_input_itemQuantity);

        // //finding the purchase per unit edit text in the Xml file
        textinputitempurchaseperunit=findViewById(R.id.text_input_itempurchaseperunit);

        //finding the purchase total edit text in the Xml file
        textinputitempurchasetotal=findViewById(R.id.text_input_itempurchasetotal);

        //finding the sale pr unit edit text in the Xml file
        textinputitemsaleunit=findViewById(R.id.text_input_itemsaleunit);

        //finding the sale total edit text in the Xml file
        textinputitemsaletotal=findViewById(R.id.text_input_itemsaletotal);

        //finding the imageView for add item image in the Xml file
        imageViewForItem=findViewById(R.id.imageview_for_item);

        //finding the textview for item id in the xml file
        textViewforid=findViewById(R.id.textview_for_id);

        //finding the unit button for show all unit in form of dialog fragment in the xml file
        unit_Button=findViewById(R.id.unit_button);

        //finding the cardview for sent the user to default item activity in the xml file
        defualtitemListCardView=findViewById(R.id.cardview_defaultList);

        //add the action listener to the cardview for sending the user to the default items activity
        defualtitemListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),DefaultItemList.class);
                startActivity(intent);
            }
        });

        //add action listener in the image view for  open camra and take image and set image in the imageview
        imageViewForItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system os is >=marshmallow,request runtime permission
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED ||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        //permission not enable request it
                        String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show pop up to show request permission
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else {
                        //permission alrady granted
                        openCamra();
                    }
                }
                else {
                    //system os<marsmellow
                    openCamra();
                }

           }
        });
    }

    //open carmra function
    private void openCamra() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NEW Pitchers");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from the camra");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        //camra intent
        Intent camraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(camraIntent,IMAGE_CAPTURE_CODE);

    }

    //HANDLING PERMISSION RESULT of open carmra
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //this method is called when use allow or deny fron premission request code


        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //premissionfor popup was granted
                    openCamra();
                }
                else {
                    //permission for popup was deni....
                    Toast.makeText(getBaseContext(),"permission denied...",Toast.LENGTH_SHORT).show();
                }
        }
    }

    //override onActivityResult method for set image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //call image was capture from camra
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image capture to the our imageview
            imageViewForItem.setImageURI(image_uri);
        }
    }


    //method for validation on name edit text
    private  boolean validateItemName(){
        String itemname=textinputitemname.getEditText().getText().toString().trim();
        if(itemname.isEmpty()){
            textinputitemname.setError("Item Name can't be empty");
            return false;
        }else {
            textinputitemname.setError(null);
            return true;
        }
    }

    //method for validation quentity edit text
    private  boolean validateItemQuntity(){
        String itemQuantity=textinputitemquantiity.getEditText().getText().toString().trim();
        if(itemQuantity.isEmpty()){
            textinputitemquantiity.setError("Item Quantity can't be empty");
            return false;
        }else {
            textinputitemquantiity.setError(null);
            return true;
        }
    }

    //method for validation for puchase unit and purchase total edit text
    private  boolean validateItemPurchasePrice(){
        String itempurchaseparunit=textinputitempurchaseperunit.getEditText().getText().toString().trim();
        String itempuchasetotal=textinputitempurchasetotal.getEditText().getText().toString().trim();
        if(itempurchaseparunit.isEmpty()&&itempuchasetotal.isEmpty()){
            textinputitempurchaseperunit.setError("Item purchase or purchase total can't be empty");
            return false;
        }else {
            textinputitempurchaseperunit.setError(null);
            return true;
        }
    }

    //method for validation sale unit and sale toale edit text
    private  boolean validateItemUnit(){
        String itemunit=textinputitemsaleunit.getEditText().getText().toString().trim();
        String itemtotalprice=textinputitemsaletotal.getEditText().getText().toString().trim();
        if(itemunit.isEmpty()&& itemtotalprice.isEmpty()){
            textinputitemsaleunit.setError("unit and total please fill one in both");
            return false;
        }else {
            textinputitemsaleunit.setError(null);
            return true;
        }
    }


    //method of save button which use to cheack all the edit text the fill then save the delait in the table
    public void addStocksInRecyclerrView(View view) {
        if(!validateItemName()  | !validateItemPurchasePrice()| !validateItemQuntity() | !validateItemUnit()){
            return;
        }
        Toast.makeText(getBaseContext(),"data should be store",Toast.LENGTH_SHORT).show();
    }

    //method of cancel button which go back to add item activity
    public void goback(View view) {
       Intent intent=new Intent(getBaseContext(),AddItem.class);
       startActivity(intent);
    }

    public void unitdothis(View view) {
        openDialog();
    }
    public void openDialog(){
        dialogFragementforunit ialogFragementforunit=new dialogFragementforunit();
        ialogFragementforunit.show(getSupportFragmentManager(),"exampledialog");
    }
}