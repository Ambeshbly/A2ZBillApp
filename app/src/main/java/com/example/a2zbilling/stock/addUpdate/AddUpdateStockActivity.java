package com.example.a2zbilling.stock.addUpdate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.a2zbilling.R;
import com.example.a2zbilling.ScannerActivity;
import com.example.a2zbilling.databinding.ActivityAddItemFloatingButtonBinding;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.stock.DefaultItemList.DefaultItemListActivity;
import com.example.a2zbilling.stock.StockActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class AddUpdateStockActivity extends AppCompatActivity {

    //final permisssion code variable declartion which is used to take permission of the camra to the user
    private static final int PERMISSION_CODE = 1000;
    //final image capture code variable declartion which is used to take image to the camra
    private static final int IMAGE_CAPTURE_CODE = 1001;
    //image path uri
    Uri image_uri;

    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    private byte[] thumb_byte;

    private static final int Gellery_Pik=2;

    private FirebaseUser mCurrentUser;

    private StorageReference mPofile;

    private int maxId=0;

    CollectionReference stockRef;

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
   // public static TextView textViewScanner;


    private String itemName;
    private double ItemQuentity;
    private String ItemPurchasePrice;
    private String ItenPurchasetotal;
    private String ItemsalePrice;
    private String ItemSaleTotal;
    private String document;
    private Stock selectedStock;

    //    FragmentTransaction t;


    public AddUpdateStockActivity() {

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String   userId = currentUser.getUid();

        stockRef = db.collection("users").document(userId).collection("stocks");
        stockRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                maxId = queryDocumentSnapshots.getDocuments().size();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_floating_button);
        setTitle("Add New Stock");


        //finding the item name edit text in the Xml file

        textinputitemname = findViewById(R.id.text_input_itemname);

     //   textViewScanner=findViewById(R.id.textview_for_scanner);

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

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = mCurrentUser.getUid();

        mPofile= FirebaseStorage.getInstance().getReference();

     //   DocumentReference stockRef= db.collection("users").document(userId).collection("stocks").document(docement);

        //finding the textview for item id in the xml file
        textViewforid = findViewById(R.id.textview_for_id);

        //finding the unit button for show all unit in form of dialog fragment in the xml file
        unit_Button = findViewById(R.id.unit_button);


        //finding the cardview for sent the user to default item activity in the xml file
        defualtitemListCardView = findViewById(R.id.cardview_defaultList);


        addUpdateStockActivityViewModel = ViewModelProviders.of(this).get(AddUpdateStockActivityViewModel.class);

        editTextName = findViewById(R.id.editName);
        editTextQuntity = findViewById(R.id.editQuentity);
        editTextPruchasePrice = findViewById(R.id.editPurchasePrice);
        editTextPurchaseTotal = findViewById(R.id.editPurchaseTotal);
        editTextSalePrice = findViewById(R.id.editSalePrice);
        editTextSaleTotal = findViewById(R.id.editSaleTotal);

        activityAddItemFloatingButtonBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item_floating_button);

        AddItemClickHandler addItemClickHandler = new AddItemClickHandler();
        activityAddItemFloatingButtonBinding.setAdditemclickHandler(addItemClickHandler);

        saveButton = findViewById(R.id.buttonSave);


        Intent intent = getIntent();
        selectedStock = (Stock) intent.getSerializableExtra("stock_object");
        document=intent.getStringExtra("documentName");

        if (selectedStock != null) {
            activityAddItemFloatingButtonBinding.setStock(selectedStock);
            saveButton.setText("update");

        } else {

            activityAddItemFloatingButtonBinding.setStock(new Stock());
        }
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
        if (requestCode==IMAGE_CAPTURE_CODE &&
                resultCode == RESULT_OK) {
            //set the image capture to the our imageview
            imageViewForItem.setImageURI(image_uri);
        }

        if (requestCode==1 &&
                resultCode == RESULT_OK) {
            String barCode=data.getExtras().getString("satva");
            activityAddItemFloatingButtonBinding.textviewForScanner.setText(barCode);
            activityAddItemFloatingButtonBinding.getStock().setBarCode(barCode);

        }

        //call when image is sellected from the gellery
        if(requestCode==Gellery_Pik &&resultCode==RESULT_OK){
            Uri imageURI=data.getData();
            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(imageURI)
                    .setAspectRatio(1,1)
                    .start(AddUpdateStockActivity.this);
        }



        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();
                final File thumb_file_path=new File(resultUri.getPath());
                String current_user_id=mCurrentUser.getUid();
                try {
                    Bitmap thumb_bitmap=new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_file_path);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                    thumb_byte=bos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                StorageReference filePath=mPofile.child("Profile").child( current_user_id+".jpg");
                final StorageReference thumb_path=mPofile.child("Profile").child("thumbs").child( current_user_id+".jpg");

                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUri=uri.toString();
                                final UploadTask uploadTask=thumb_path.putBytes(thumb_byte);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String thumb_downloadUrl=uri.toString();
                                              /*  DocumentReference stockDoc = stockRef.document(Integer.toString(maxId+1));
                                                stockDoc.update("image",downloadUri).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getBaseContext(),"sucessful uploaded",Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                stockDoc.update("thumb_image",thumb_downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getBaseContext(),"sucessful uploaded",Toast.LENGTH_SHORT).show();
                                                    }
                                                });*/

                                                activityAddItemFloatingButtonBinding.getStock().setImage(downloadUri);
                                                activityAddItemFloatingButtonBinding.getStock().setThumb_image(thumb_downloadUrl);
                                                imageViewForItem.setImageURI(resultUri);
                                                Toast.makeText(getBaseContext(),"sucessful uploaded",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getBaseContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    //method for validation on name edit text
    private boolean validateItemName(Stock stock) {
        String itemname = stock.getName();
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
        double itemQuantity = stock.getPrimaryQuant();
        if (itemQuantity == 0) {
            textinputitemquantiity.setError("Item Quantity can't be empty");
            return false;
        } else {
            textinputitemquantiity.setError(null);
            return true;
        }
    }

    //method for validation for puchase unit and purchase total edit text
    private boolean validateItemPurchasePrice(Stock item) {
        String itempurchaseparunit = item.getPurchasePerUnit();
        String itempuchasetotal = item.getPurchaseTotal();
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

        if (stock.getPriamryUnit().contentEquals(Unit.UNIT_DEFAULT)) {
            new AlertDialog.Builder(AddUpdateStockActivity.this)
                    .setTitle("Unit Selection Failed")
                    .setMessage("Please select unit.")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return false;
        } else {
            return true;
        }
    }


    public class AddItemClickHandler {

        //method of save button which use to cheack all the edit text the fill then save the delait in the table
        public void onSaveClick(View view) {



            Stock stock = activityAddItemFloatingButtonBinding.getStock();
            if (!validateItemName(stock) | !validateItemPurchasePrice(stock) | !validateItemQuntity(stock) | !validateItemUnit(stock)) {
                return;
            }
            stock.updateSecondQuant(stock.getPrimaryQuant()*stock.getPcPerBox());

            if (selectedStock != null) {
                // request is to update the stock.

                //addUpdateStockActivityViewModel.update(stock);
                addUpdateStockActivityViewModel.updatetStock(stock,Integer.toString(stock.getId()));
                finish();

            } else {
                // request is to add new stock.
                Intent intent = new Intent().putExtra("stock", stock);

                setResult(RESULT_OK, intent);
                finish();

            }

        }

        //method of cancel button which go back to add item activity
        public void onCancelClick(View view) {
            Intent intent = new Intent(getBaseContext(), StockActivity.class);
            startActivity(intent);
        }

        //method for barCode Click Listener
        public void onBarCodeClick(View view){
            Intent intent=new Intent(AddUpdateStockActivity.this, ScannerActivity.class);
            startActivityForResult(intent,1);
        }


        public void selectUnit(View view) {
            unit_Button.setBackgroundColor(Color.GREEN);
            UnitDialogFragement ialogFragementforunit = new UnitDialogFragement(activityAddItemFloatingButtonBinding.getStock());
            ialogFragementforunit.show(getSupportFragmentManager(), "exampledialog");


        }

        public void setImage(View view) {
           /* //if system os is >=marshmallow,request runtime permission
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
            }*/


           //after the default button click it go to the gellery to chosse the image of item
            Intent gellery_ntent=new Intent();
            gellery_ntent.setType("image/*");
            gellery_ntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gellery_ntent,"SELECT IMAGE"),Gellery_Pik);

        }


        //add the action listener to the cardview for sending the user to the default items activity
        public void showDefaultItems(View view) {
            Intent intent = new Intent(getBaseContext(), DefaultItemListActivity.class);
            startActivity(intent);

        }

    }



}
