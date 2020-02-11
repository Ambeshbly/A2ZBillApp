package com.example.a2zbilling.stock.addUpdate;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.R;
import com.example.a2zbilling.databinding.ActivityAddItemFloatingButtonBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.DefaultItemList.DefaultItemListActivity;
import com.example.a2zbilling.stock.StockActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddUpdateStockActivity extends AppCompatActivity {

    //final permisssion code variable declartion which is used to take permission of the camra to the user
    private static final int PERMISSION_CODE = 1000;
    //final image capture code variable declartion which is used to take image to the camra
    private static final int IMAGE_CAPTURE_CODE = 1001;
    //image path uri
    Uri image_uri;
    AddUpdateStockActivityViewModel addUpdateStockActivityViewModel;
    //EditText declaration
    private TextInputLayout textinputitemname, textinputitemquantiity, textinputitempurchaseperunit, textinputitempurchasetotal, textinputitemsaleunit, textinputitemsaletotal;
    private TextInputEditText editTextName, editTextQuntity, editTextPruchasePrice, editTextPurchaseTotal, editTextSalePrice, editTextSaleTotal;
    //image View delcartion
    private ImageView imageViewForItem;
    //declation of textview for show id of the items
    private TextView textViewforid;
    //declartion of unit button for show all unit to the user in the form on dialog fragmnets
    private Button unit_Button, saveButton;
    //declation of cardview which is used to send the default items activity
    private CardView defualtitemListCardView;
    private ActivityAddItemFloatingButtonBinding activityAddItemFloatingButtonBinding;


    private String itemName, ItemQuentity, ItemPurchasePrice, ItenPurchasetotal, ItemsalePrice, ItemSaleTotal;

    //    FragmentTransaction t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_floating_button);

        //finding the item name edit text in the Xml file

        textinputitemname = findViewById(R.id.text_input_itemname);

        //finding the item quentity edit text in the Xml file
        textinputitemquantiity = findViewById(R.id.text_input_itemQuantity);

        // //finding the purchase per unit edit text in the Xml file
        textinputitempurchaseperunit = findViewById(R.id.text_input_itempurchaseperunit);

        //finding the purchase total edit text in the Xml file
        textinputitempurchasetotal = findViewById(R.id.text_input_itempurchasetotal);

        //finding the sale pr unit edit text in the Xml file
        textinputitemsaleunit = findViewById(R.id.text_input_itemsaleunit);

        //finding the sale total edit text in the Xml file
        textinputitemsaletotal = findViewById(R.id.text_input_itemsaletotal);

        //finding the imageView for add item image in the Xml file
        imageViewForItem = findViewById(R.id.imageview_for_item);

        //finding the textview for item id in the xml file
        textViewforid = findViewById(R.id.textview_for_id);

        //finding the unit button for show all unit in form of dialog fragment in the xml file
        unit_Button = findViewById(R.id.unit_button);

        //finding the cardview for sent the user to default item activity in the xml file
        defualtitemListCardView = findViewById(R.id.cardview_defaultList);


        Stock stock = new Stock();
        activityAddItemFloatingButtonBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item_floating_button);
        activityAddItemFloatingButtonBinding.setStock(stock);
        AddItemClickHandler addItemClickHandler = new AddItemClickHandler();
        activityAddItemFloatingButtonBinding.setAdditemclickHandler(addItemClickHandler);

        addUpdateStockActivityViewModel = ViewModelProviders.of(this).get(AddUpdateStockActivityViewModel.class);

        editTextName = findViewById(R.id.editName);
        editTextQuntity = findViewById(R.id.editQuentity);
        editTextPruchasePrice = findViewById(R.id.editPurchasePrice);
        editTextPurchaseTotal = findViewById(R.id.editPurchaseTotal);
        editTextSalePrice = findViewById(R.id.editSalePrice);
        editTextSaleTotal = findViewById(R.id.editSaleTotal);

        saveButton = findViewById(R.id.buttonSave);


        Intent intent = getIntent();
        if (intent.hasExtra("Item_id")) {
            itemName = intent.getStringExtra("Item_name");
            ItemQuentity = intent.getStringExtra("Item_quentity");
            ItemPurchasePrice = intent.getStringExtra("Item_purchasePrice");
            ItenPurchasetotal = intent.getStringExtra("Item_purchaseTotal");
            ItemsalePrice = intent.getStringExtra("Item_salePrice");
            ItemSaleTotal = intent.getStringExtra("Item_saleTotal");

            saveButton.setText("update");

            editTextName.setHint(itemName);
            editTextName.setHintTextColor(Color.BLUE);
            editTextQuntity.setHint(ItemQuentity);
            editTextQuntity.setHintTextColor(Color.BLUE);
            editTextPruchasePrice.setHint(ItemPurchasePrice);
            editTextPruchasePrice.setHintTextColor(Color.BLUE);
            editTextPurchaseTotal.setHint(ItenPurchasetotal);
            editTextPurchaseTotal.setHintTextColor(Color.BLUE);
            editTextSalePrice.setHint(ItemsalePrice);
            editTextSalePrice.setHintTextColor(Color.BLUE);
            editTextSaleTotal.setHint(ItemSaleTotal);
            editTextSaleTotal.setHintTextColor(Color.BLUE);
        }


        //        FragmentManager manager=getSupportFragmentManager();
        //        t=manager.beginTransaction();


    }

    //open carmra function
    private void openCamra() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NEW Pitchers");
        values.put(MediaStore.Images.Media.DESCRIPTION, "from the camra");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //camra intent
        Intent camraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(camraIntent, IMAGE_CAPTURE_CODE);

    }

    //HANDLING PERMISSION RESULT of open carmra
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //this method is called when use allow or deny fron premission request code

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //premissionfor popup was granted
                    openCamra();
                } else {
                    //permission for popup was deni....
                    Toast.makeText(getBaseContext(), "permission denied...", Toast.LENGTH_SHORT).show();
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
    private boolean validateItemName(Stock stock) {
        String itemname = stock.getItemName();
        if (itemname.isEmpty()) {
            textinputitemname.setError("Item Name can't be empty");
            return false;
        } else {
            textinputitemname.setError(null);
            return true;
        }
    }

    //method for validation quentity edit text
    private boolean validateItemQuntity(Stock stock) {
        String itemQuantity = stock.getItemQuentity();
        if (itemQuantity.isEmpty()) {
            textinputitemquantiity.setError("Item Quantity can't be empty");
            return false;
        } else {
            textinputitemquantiity.setError(null);
            return true;
        }
    }

    //method for validation for puchase unit and purchase total edit text
    private boolean validateItemPurchasePrice(Stock item) {
        String itempurchaseparunit = item.getItemPurchasePerUnit();
        String itempuchasetotal = item.getItemPuchaseTotal();
        if (itempurchaseparunit.isEmpty() && itempuchasetotal.isEmpty()) {
            textinputitempurchaseperunit.setError("Item purchase or purchase total can't be empty");
            return false;
        } else {
            textinputitempurchaseperunit.setError(null);
            return true;
        }
    }

    //method for validation sale unit and sale toale edit text
    private boolean validateItemUnit(Stock stock) {
        String itemunit = stock.getItemSalePerUnit();
        String itemtotalprice = stock.getItemSaleTotal();
        if (itemunit.isEmpty() && itemtotalprice.isEmpty()) {
            textinputitemsaleunit.setError("unit and total please fill one in both");
            return false;
        } else {
            textinputitemsaleunit.setError(null);
            return true;
        }
    }


    public class AddItemClickHandler {

        //method of save button which use to cheack all the edit text the fill then save the delait in the table
        public void onSaveClick(View view) {

            int id = getIntent().getIntExtra("Item_id", -1);
            if (id != -1) {

                Stock stock = activityAddItemFloatingButtonBinding.getStock();

                String Name = editTextName.getText().toString().trim();
                if (Name.isEmpty()) {
                    editTextName.setText(itemName);
                    stock.setItemName(itemName);
                } else {
                    editTextName.setText(Name);
                    stock.setItemName(Name);
                }

                String Quentity = editTextQuntity.getText().toString().trim();
                if (Quentity.isEmpty()) {
                    editTextQuntity.setText(ItemQuentity);
                    stock.setItemQuentity(ItemQuentity);
                } else {
                    editTextQuntity.setText(Quentity);
                    stock.setItemQuentity(Quentity);
                }

                String Purchaseprice = editTextPruchasePrice.getText().toString().trim();
                if (Purchaseprice.isEmpty()) {
                    editTextPruchasePrice.setText(ItemPurchasePrice);
                    stock.setItemPurchasePerUnit(ItemPurchasePrice);
                } else {
                    editTextPruchasePrice.setText(Purchaseprice);
                    stock.setItemPurchasePerUnit(Purchaseprice);
                }

                String Purchasetotal = editTextPurchaseTotal.getText().toString().trim();
                if (Purchasetotal.isEmpty()) {
                    editTextPurchaseTotal.setText(ItenPurchasetotal);
                    stock.setItemPuchaseTotal(ItenPurchasetotal);
                } else {
                    editTextPurchaseTotal.setText(Purchasetotal);
                    stock.setItemPuchaseTotal(Purchasetotal);
                }

                String salePrice = editTextSalePrice.getText().toString().trim();
                if (salePrice.isEmpty()) {
                    editTextSalePrice.setText(ItemsalePrice);
                    stock.setItemSalePerUnit(ItemsalePrice);
                } else {
                    editTextSalePrice.setText(salePrice);
                    stock.setItemSalePerUnit(salePrice);
                }

                String Saletotal = editTextSaleTotal.getText().toString().trim();
                if (Saletotal.isEmpty()) {
                    editTextSaleTotal.setText(ItemSaleTotal);
                    stock.setItemSaleTotal(ItemSaleTotal);
                } else {
                    editTextSaleTotal.setText(Saletotal);
                    stock.setItemSaleTotal(Saletotal);
                }

                stock.setItemId(id);
                addUpdateStockActivityViewModel.update(stock);
                Toast.makeText(getBaseContext(), "data updated", Toast.LENGTH_SHORT).show();
                finish();

            } else {

                Stock stock = activityAddItemFloatingButtonBinding.getStock();
                if (!validateItemName(stock) | !validateItemPurchasePrice(stock) | !validateItemQuntity(stock) | !validateItemUnit(stock)) {
                    return;
                }

                Intent intent = new Intent().putExtra("stock", stock);
                setResult(RESULT_OK, intent);

                // stock is new stock which need to be passed to AddStockFragment.


                //addUpdateStockActivityViewModel.insert(stock);


                //
                //
                //  int Itemid=stock.getItemId();
                //                        String Name= editTextName.getText().toString().trim();
                //                        String Quentity= editTextQuntity.getText().toString().trim();
                //                        String unit=stock.getItemUnit();
                //                        String Purchaseprice= editTextPruchasePrice.getText().toString().trim();
                //                        String Purchasetotal= editTextPurchaseTotal.getText().toString().trim();
                //                        String salePrice= editTextSalePrice.getText().toString().trim();
                //                        String Saletotal= editTextSaleTotal.getText().toString().trim();

                //
                //                        activityAddItemFloatingButtonBinding.getStock().setItemName(Name);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemQuentity(Quentity);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemUnit(unit);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemPurchasePerUnit(Purchaseprice);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemPuchaseTotal(Purchasetotal);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemSalePerUnit(salePrice);
                //                         activityAddItemFloatingButtonBinding.getStock().setItemSaleTotal(Saletotal);

                //
                //                        AddStockFragment addStockFragment=new AddStockFragment(stock);


                //                         Intent intent=new Intent(AddUpdateStockActivity.this, AddStockFragment.class);
                //
                //                         startActivity(intent);

                //                        intent.putExtra("Item_ID",1);
                //                        intent.putExtra("Item_NAME",Name);
                //                        intent.putExtra("Item_QUNTITY",Quentity);
                //                        intent.putExtra("Item_UNIT",unit);
                //                        intent.putExtra("Item_PURCHASEPRICE",Purchaseprice);
                //                        intent.putExtra("Item_PURCHASETOTAL",Purchasetotal);
                //                        intent.putExtra("Item_SALEPRICE",salePrice);
                //                        intent.putExtra("Item_SALETOTAL",Saletotal);
                //
                //                        startActivity(intent);


                //                        Bundle bundle = new Bundle();
                //                        bundle.putString("edttext", "From Activity");
                //                        // set Fragmentclass Arguments
                //                        AddStockFragment fragobj = new AddStockFragment();
                //                        fragobj.setArguments(bundle);
                //                        t.commit();
                //


                //AddStockFragment.stockdata(stock);
                Toast.makeText(getBaseContext(), "data save", Toast.LENGTH_SHORT).show();
                finish();

            }

        }

        //method of cancel button which go back to add item activity
        public void onCancelClick(View view) {
            Intent intent = new Intent(getBaseContext(), StockActivity.class);
            startActivity(intent);
        }

        public void selectUnit(View view) {
            dialogFragementforunit ialogFragementforunit = new dialogFragementforunit(activityAddItemFloatingButtonBinding);
            ialogFragementforunit.show(getSupportFragmentManager(), "exampledialog");
        }

        public void setImage(View view) {
            //if system os is >=marshmallow,request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission not enable request it
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //show pop up to show request permission
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    //permission alrady granted
                    openCamra();
                }
            } else {
                //system os<marsmellow>
                openCamra();
            }
        }


        //add the action listener to the cardview for sending the user to the default items activity
        public void showDefaultItems(View view) {
            Intent intent = new Intent(getBaseContext(), DefaultItemListActivity.class);
            startActivity(intent);

        }

    }


}
