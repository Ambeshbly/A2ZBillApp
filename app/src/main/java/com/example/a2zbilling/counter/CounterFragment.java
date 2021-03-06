package com.example.a2zbilling.counter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2zbilling.DateConverter;
import com.example.a2zbilling.MainActivity;
import com.example.a2zbilling.MainActivityViewModel;
import com.example.a2zbilling.R;
import com.example.a2zbilling.counter.BillList.BillHistoryActivity;
import com.example.a2zbilling.counter.Selling.SellingStocksActivity;
import com.example.a2zbilling.counter.SuspendedBills.DialogFragmentForSespendList;
import com.example.a2zbilling.counter.SuspendedBills.SuspendedTransactionListActivity;
import com.example.a2zbilling.databinding.FragmentCounterBinding;
import com.example.a2zbilling.db.entities.Customer;
import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.ShopDetail;
import com.example.a2zbilling.db.entities.Stock;
import com.example.a2zbilling.db.entities.Suspend;
import com.example.a2zbilling.db.entities.SuspendDetail;
import com.example.a2zbilling.stock.addUpdate.AddUpdateStockActivity;
import com.example.a2zbilling.stock.addUpdate.Unit;
import com.example.a2zbilling.stock.addUpdate.UnitDialogFragement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;

public class CounterFragment extends Fragment  {

    public static final int ADD_NEW_STOCK_REQ_CODE = 1;
    public static final String TAG_SALE_STOCK_OBJ = "Sale_Stock_Obj";
    public static final String TAG_AVAILABLE_STOCK_OBJ = "Available_Stock_Obj";
    private ArrayList<Stock> stockList;
    private int maxId=0,maxSuspendDetailId=0;

    CollectionReference saleRef,suspendDetailRef;
    //get database refrence of fireStore Database
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    //get userCurrent id
    FirebaseUser  currentUser =FirebaseAuth.getInstance().getCurrentUser();
    String  userId = currentUser.getUid();

    CounterAdapterForPriceQntyValue counterAdapterForPriceQntyValue;
    MediaPlayer mediaPlayer;
    EditText editTextOtherName,editTextValue;
    FloatingActionButton otherButton;
    static  int itemNo=0;
    private TextView textViewShopName,textViewShopPhoneNo,textViewShopEmail;
    private MainActivityViewModel mainActivityViewModel;
    private List<Customer> customerList;


    //custrouctor
    public CounterFragment(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
        mainActivityViewModel.setSale(new Sales());


          //count the number of suspend list
            saleRef = db.collection("users").document(userId).collection("suspend");
            saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    maxId = queryDocumentSnapshots.getDocuments().size();
                }
            });

            //count the number of suspent detail list
            suspendDetailRef = db.collection("users").document(userId).collection("suspend detail");
            saleRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    maxSuspendDetailId = queryDocumentSnapshots.getDocuments().size();
                }
            });


    }

    //onCreateView Override method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_counter, container, false);
        FragmentCounterBinding counterBinding = DataBindingUtil.bind(view);
        counterBinding.setSale(mainActivityViewModel.getSale());

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.simple);




        //textViewTotal = view.findViewById(R.id.textView_counter_total);
        textViewShopName=view.findViewById(R.id.TextView_for_shopName);
        textViewShopPhoneNo=view.findViewById(R.id.Text_VIew_for_phone_no);
        textViewShopEmail=view.findViewById(R.id.email);
        editTextOtherName=view.findViewById(R.id.other_stock);
        editTextValue=view.findViewById(R.id.other_value);
        otherButton=view.findViewById(R.id.other_button);

        RecyclerView recyclerViewForPriceQntyValue = view.findViewById(R.id.recyclerView_for_Price_qunty_value);
        recyclerViewForPriceQntyValue.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForPriceQntyValue.setHasFixedSize(true);

        counterAdapterForPriceQntyValue=new CounterAdapterForPriceQntyValue();
        recyclerViewForPriceQntyValue.setAdapter(counterAdapterForPriceQntyValue);

        mainActivityViewModel.getNewlyAddedStocks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Stock>>() {
            @Override
            public void onChanged(ArrayList<Stock> stocks) {
                counterAdapterForPriceQntyValue.setItems(stocks);
            }
        });


        //---------------------------delete the item from the counter by swiped---------------------------

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();

                if(direction==ItemTouchHelper.LEFT)//swiped to left
                {
                    Stock item=counterAdapterForPriceQntyValue.getStockAtPosition(viewHolder.getAdapterPosition());
                    stockList.remove(item);
                    counterAdapterForPriceQntyValue.setItems(stockList);
                    Toast.makeText(getContext(),"remove",Toast.LENGTH_SHORT).show();
                }
                if(direction==ItemTouchHelper.RIGHT)//swiped to right
                { Stock item=counterAdapterForPriceQntyValue.getStockAtPosition(viewHolder.getAdapterPosition());
                    mainActivityViewModel.setStock(item);
                    DialogFragmentForUpdateCounterStock dialogFragmentForUpdateCounterStock = new DialogFragmentForUpdateCounterStock(mainActivityViewModel,counterAdapterForPriceQntyValue,viewHolder.getAdapterPosition());
                    dialogFragmentForUpdateCounterStock.show(getActivity().getSupportFragmentManager(), "UpdateStock");
                    counterAdapterForPriceQntyValue.setItems(stockList);
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(dX>0)//swiped to right
                {
                    new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.limeGreen))
                            .addActionIcon(R.drawable.ic_update)
                            .addSwipeRightLabel("update")
                            .create()
                            .decorate();
                }
                if(dX<0)//swiped to left
                {
                    new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                            .addActionIcon(R.drawable.ic_delete)
                            .addSwipeLeftLabel("remove")
                            .create()
                            .decorate();

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerViewForPriceQntyValue);



        mainActivityViewModel.getAllCustomer().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                customerList = customers;
            }
        });
        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_STOCK_REQ_CODE && resultCode == RESULT_OK) {

            //this is first object
            Stock stock = (Stock) data.getSerializableExtra(TAG_SALE_STOCK_OBJ);

            //this id second object which is need to update when proceed button is click
            Stock soldStock = (Stock) data.getSerializableExtra(TAG_AVAILABLE_STOCK_OBJ);

            mainActivityViewModel.getSoldStocksList().add(soldStock);

            //   String itemName = stock.getItemName();
            mainActivityViewModel.addNewlyAddedStock(stock);
            ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();

            Sales sale = mainActivityViewModel.getSale();

            for (int i = 0; i < stockList.size(); i++) {

                CounterFragment fragment = (CounterFragment)
                        getFragmentManager().findFragmentById(R.id.fragment_conterner);
                         getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
            }

            sale.setTotalBillAmt(Double.toString(Double.parseDouble(sale.getTotalBillAmt()) + Double.parseDouble(stock.getSaleTotal())));

            Calendar calendar=Calendar.getInstance();
            Long date= DateConverter.fromDate(calendar.getTime());
            sale.setDate(date);

        }

    }

    @Override
    public void onStart() {
        CardView cardView_waitList = getView().findViewById(R.id.waitlistcardview);
        CardView cardView_conformList = getView().findViewById(R.id.conformListcardview);
        CardView cardView_suspend = getView().findViewById(R.id.cardview_defaultList);


        //if sale totalBillAmount is 0 its means its come from the suspend list
        String tot=mainActivityViewModel.getSale().getTotalBillAmt();
        if(tot=="0"){
            double totalBillAmount=0;
            for (int i = 0; i <mainActivityViewModel.getNewlyAddedStocks().getValue().size() ; i++) {
                Stock stock=mainActivityViewModel.getNewlyAddedStocks().getValue().get(i);
                double t=Double.parseDouble(stock.getSaleTotal());
                totalBillAmount=totalBillAmount+t;
            }
            mainActivityViewModel.getSale().setTotalBillAmt(Double.toString(totalBillAmount));
        }


        cardView_suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: suspend work here
                mainActivityViewModel.getSoldStocksList().clear();
                ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();

                if (stockList.isEmpty()) {
                    Toast.makeText(getContext(), "please add the item first", Toast.LENGTH_SHORT).show();
                } else {
                    DialogFragmentForSespendList dialogFragmentForSespendList = new DialogFragmentForSespendList(   mainActivityViewModel.getNewlyAddedStocks().getValue(),mainActivityViewModel,counterAdapterForPriceQntyValue);
                    dialogFragmentForSespendList.show(getChildFragmentManager(), "exampledialog");
                }


            }
        });

        CardView cardView_Proceed = getView().findViewById(R.id.cardview_proceed);
        cardView_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Sales sale = mainActivityViewModel.getSale();
                Calendar calendar=Calendar.getInstance();
                Long date= DateConverter.fromDate(calendar.getTime());
                sale.setDate(date);
                ArrayList<Stock> stockList = mainActivityViewModel.getNewlyAddedStocks().getValue();

                if (stockList.isEmpty()) {
                    Toast.makeText(getContext(), "please add the item first", Toast.LENGTH_SHORT).show();
                } else {
                    //double total = Double.parseDouble(mainActivityViewModel.getSale().getTotalBillAmt());
                    PaymentDialogFragment dialogFragment = new PaymentDialogFragment(mainActivityViewModel,counterAdapterForPriceQntyValue, customerList);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "exampledialog");
                    //textViewTotal.setText("0.00");
                }
            }
        });

        super.onStart();

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.bt_float);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), SellingStocksActivity.class);
                startActivityForResult(intent, ADD_NEW_STOCK_REQ_CODE);
            }
        });


        cardView_waitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), SuspendedTransactionListActivity.class);
                startActivity(intent);
            }
        });


        cardView_conformList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(getContext(), BillHistoryActivity.class);
                startActivity(intent);
            }
        });


        mainActivityViewModel.getAllShopDetail().observe(getViewLifecycleOwner(), new Observer<List<ShopDetail>>() {
            @Override
            public void onChanged(List<ShopDetail> shopDetails) {
                for (int i=0;i<shopDetails.size();i++){
                ShopDetail shopDetail=shopDetails.get(i);
                textViewShopName.setText(shopDetail.getShopName());
                textViewShopPhoneNo.setText(shopDetail.getPhoneNo());
                textViewShopEmail.setText(shopDetail.getEmail());
              }
            }
        });
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validationOtherItem()) {
                    return;
                }
                Stock stock=new Stock();
                itemNo++;
                stock.setName(itemNo+" item");
                stock.setPrimaryQuant(1);
                stock.setSalePerUnit(editTextValue.getText().toString().trim());

                Sales sale = mainActivityViewModel.getSale();
                double totalBillAmt = Double.parseDouble(sale.getTotalBillAmt()) + Double.parseDouble(stock.getSalePerUnit());
                sale.setTotalBillAmt(Double.toString(totalBillAmt));

                mainActivityViewModel.addNewlyAddedStock(stock);
                editTextValue.setText("");
                CounterFragment fragment = (CounterFragment)
                        getFragmentManager().findFragmentById(R.id.fragment_conterner);
                        getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();

            }
        });
    }

    private boolean validationOtherItem() {
        if (editTextValue.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Please enter the value",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
