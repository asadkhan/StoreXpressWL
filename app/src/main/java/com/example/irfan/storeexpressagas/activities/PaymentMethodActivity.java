package com.example.irfan.storeexpressagas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.Adapters.AddressListAdapter;
import com.example.irfan.storeexpressagas.Adapters.CartItemListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CartRequest;
import com.example.irfan.storeexpressagas.models.GResponse;
import com.example.irfan.storeexpressagas.models.ItemVM;
import com.example.irfan.storeexpressagas.models.OrderModel;
import com.example.irfan.storeexpressagas.models.OrderRequest;
import com.example.irfan.storeexpressagas.models.OrderResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
public Button btnFinish;
public RadioButton radio_cod;
    public RecyclerView recyclerViewAdress;

    public AddressListAdapter mAdapter;
public List<AddressResponse.Value> adddressLst = new ArrayList<>();

    public TextView tv,txt_add_address;
    public ImageView i;

    private static final int ADD_ADDRESS_ACTIVITY_REQUEST_CODE_DLIVERY = 1;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

    radio_cod=(RadioButton) findViewById(R.id.radio_cod);
        btnFinish=(Button)findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(this);
    txt_add_address =(TextView) findViewById(R.id.txt_add_address);
    txt_add_address.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_method);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_payment_method);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_paymentmethod);
        navigationView.setNavigationItemSelectedListener(this);
    recyclerViewAdress = (RecyclerView) findViewById(R.id.recycler_view_adresses);

    mAdapter = new AddressListAdapter(this.adddressLst,this);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    radio_cod.setChecked(true);
    // RecyclerView.ItemDecoration itemDecoration =
    //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
    //recyclerViewCart.addItemDecoration(itemDecoration);

    recyclerViewAdress.setHasFixedSize(true);
    recyclerViewAdress.setLayoutManager(mLayoutManager);
    //recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerViewAdress.setAdapter(this.mAdapter);
    HideShowLogout(navigationView);


    OrderRequest.OrderType=2;
    OrderRequest.PaymentMeathod=1;
    OrderRequest.PaymentStatus=1;
    OrderRequest.Address="";

    getAddresses();


    }



    @Override
    public void onClick(View v) {
        Log.d("test","Next click");
        Log.d("test",String.valueOf(OrderRequest.OrderType));
        switch (v.getId()) {

            case R.id.txt_add_address:
                Intent intent = new Intent(this, AddAddressDailog.class);
                startActivityForResult(intent, ADD_ADDRESS_ACTIVITY_REQUEST_CODE_DLIVERY);
                break;


            case R.id.btn_finish:
                //openActivity(OrdersActivity.class);
                  //  finish();
                placeDiliveryOrder();

                break;
            case R.id.actionbar_notifcation_img:
                openActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                Log.d("test","show msg call");
                //  showMessageDailogNextScreen("test","testing message",Login.class);
                openActivity(CartActivity.class);
                break;



        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == ADD_ADDRESS_ACTIVITY_REQUEST_CODE_DLIVERY) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                String returnString = data.getStringExtra("Added");

                // set text view with string
                int added=Integer.valueOf(returnString);

                if(added==1){

                    getAddresses();
                }
            }
        }
    }

    public void getAddresses(){
        Log.d("test","place oder call");
        showProgress();

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).getAddresses().enqueue(new GeneralCallBack<AddressResponse>(this) {
            @Override
            public void onSuccess(AddressResponse response) {

                if(!response.getIserror()) {
                     adddressLst.clear();
                    OrderRequest.Address="";
                    Gson gson = new Gson();
                    String Reslog = gson.toJson(response);
                    Log.d("test", Reslog);
                    List<AddressResponse.Value> lst=response.getValue();
                    for(AddressResponse.Value obj : lst){

                        adddressLst.add(obj);
                    }

                    mAdapter.notifyDataSetChanged();


                }hideProgress();




            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");

            }



        });
    }



    public void finish(){
    openActivity(OrderStatusDeliveryActivity.class);

}
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_about);
        /*if (id == R.id.menu_about) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            openActivityWithFinish(AboutActivity.class);

        } else*/ if (id == R.id.menu_home) {
            mDrawerLayout.closeDrawers();
            openActivity(MainActivity.class);
            // MenuHandler.tracking(this);

        } else if (id == R.id.menu_cart) {
            mDrawerLayout.closeDrawers();
            //MenuHandler.currentOrders(this);
            openActivity(CartActivity.class);
        }
        else if (id == R.id.menu_pro_req) {
            mDrawerLayout.closeDrawers();
            openActivityProductRequest();
            //MenuHandler.orderHistory(this);

        }
        else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            openActivityProfile();

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }

        else if (id == R.id.menu_shopping) {
            mDrawerLayout.closeDrawers();
            openActivity(ShoppingListActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }

        else if (id == R.id.menu_orders) {
            mDrawerLayout.closeDrawers();
            openActivityOrders();

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }

        else if (id == R.id.menu_all_cat) {
            mDrawerLayout.closeDrawers();
            openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }



        else if (id == R.id.menu_logout) {
            //MenuHandler.logOut(this);
        logOut();
        }



//         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        i =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText(String.valueOf(Cart.getCartTotalItem(this)));
        i.setOnClickListener(this);
        tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    public void placeDiliveryOrder(){

    if( OrderRequest.Address=="" ||  OrderRequest.Address.isEmpty()){

        Toast.makeText(this,"Please Select Address",
                Toast.LENGTH_LONG).show();

return;
    }






        showProgress();

        List<ItemVM> itemlst = new ArrayList<>();
        List<Cart> cartItems = Cart.getCart(this);


        if(cartItems.size() <1){

            Toast.makeText(this,"Cart is empty!",
                    Toast.LENGTH_LONG).show();

            return;
        }


        //  Log.d("test",Auth.getToken(c));

        for(Cart obj : cartItems){
            ItemVM Iobj= new ItemVM();
            Iobj.Id=obj.ItemID;
            Iobj.Quantity=obj.ItemQty;
            itemlst.add(Iobj);
        }
        CartRequest cart = new CartRequest();
        cart.items=itemlst;

        Gson gson = new Gson();
        String Reslog= gson.toJson(cart);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).test(cart).enqueue(new GeneralCallBack<GResponse>(this) {
            @Override
            public void onSuccess(GResponse response) {

                if(!response.getIserror()) {

                    Gson gson = new Gson();
                    String Reslog = gson.toJson(response);
                    Log.d("test", Reslog);

                    placeOrderToServer();

                }
                hideProgress();




            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");

            }



        });


    }


    public void placeOrderToServer(){

        showProgress();
        OrderModel obj = new OrderModel();
        obj.OrderType=2;
        obj.PaymentMeathod=1;
        obj.PaymentStatus=1;
        obj.Address=OrderRequest.Address;

        Gson gson = new Gson();
        String Reslog= gson.toJson(obj);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).placeORder(obj).enqueue(new GeneralCallBack<OrderResponse>(this) {
            @Override
            public void onSuccess(OrderResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("testme", Reslog);

                if(!response.getIserror()){
                    Cart.ClearCart(PaymentMethodActivity.this);
                    if(response.getValue().getOrderType()==2) {


                        OrderStatusDeliveryActivity.orderid=response.getValue().getOrderId();
                        openActivity(OrderStatusDeliveryActivity.class);
                    }
                }

                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");

            }



        });
    }

}
