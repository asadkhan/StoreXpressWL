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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.AddressListAdapter;
import com.example.irfan.storeexpressagas.Adapters.AddressListProfileAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.extras.PrefManager;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CartRequest;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.GResponse;
import com.example.irfan.storeexpressagas.models.ItemVM;
import com.example.irfan.storeexpressagas.models.ProfileDetailsResponse;
import com.example.irfan.storeexpressagas.models.ProfileResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{
    private static final int ADD_ADDRESS_ACTIVITY_REQUEST_CODE = 0;
    public TextView tv,txt_add_newaddress,txt_profile_lbl;
    public ImageView i;
    public RecyclerView recyclerViewAdress;
    private Button btn_save;

    public AddressListProfileAdapter mAdapter;
    public List<AddressResponse.Value> adddressLst = new ArrayList<>();

    public EditText et_name,et_email,et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_profile);
        navigationView.setNavigationItemSelectedListener(this);
        et_name =(EditText) findViewById(R.id.et_name);
        et_email =(EditText) findViewById(R.id.et_email);
        et_phone =(EditText) findViewById(R.id.et_phone);
        btn_save=(Button)findViewById(R.id.btn_save);
        txt_add_newaddress=(TextView) findViewById(R.id.txt_add_newaddress);
        recyclerViewAdress = (RecyclerView) findViewById(R.id.recycler_view_adresses);
        txt_profile_lbl=(TextView)findViewById(R.id.txt_profile_lbl);
        txt_profile_lbl.requestFocus();
        mAdapter = new AddressListProfileAdapter(this.adddressLst,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewCart.addItemDecoration(itemDecoration);

        recyclerViewAdress.setHasFixedSize(true);
        recyclerViewAdress.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdress.setAdapter(this.mAdapter);
        txt_add_newaddress.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        HideShowLogout(navigationView);
        getProfile();
    }



    public void getProfile(){
        showProgress();

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).getProfile().enqueue(new GeneralCallBack<ProfileDetailsResponse>(this) {
            @Override
            public void onSuccess(ProfileDetailsResponse response) {

                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);
                hideProgress();
                if(!response.getIserror()){

                    et_name.setText( response.getValue().getFullName());
                    et_email.setText(response.getValue().getEmail());
                    et_email.setEnabled(false);
                    et_phone.setText(response.getValue().getPhoneNumber());

                    getAddresses();
                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");

            }



        });

    }


    public void getAddresses(){
        Log.d("test","place oder call");
        showProgress();
        adddressLst.clear();
        RestClient.getAuthAdapterToekn(Auth.getToken(this)).getAddresses().enqueue(new GeneralCallBack<AddressResponse>(this) {
            @Override
            public void onSuccess(AddressResponse response) {

                if(!response.getIserror()) {
                    adddressLst.clear();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
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
           // MenuHandler.logOut(this);
        logOut();
        }

//         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.actionbar_notifcation_img:
                openActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                Log.d("test","show msg call");
                //  showMessageDailogNextScreen("test","testing message",Login.class);
                openActivity(CartActivity.class);
                break;


            case R.id.txt_add_newaddress:
                Log.d("test","show msg call");
                //  showMessageDailogNextScreen("test","testing message",Login.class);
             //   openActivity(AddAddressDailog.class);
                Intent intent = new Intent(this, AddAddressDailog.class);
                startActivityForResult(intent, ADD_ADDRESS_ACTIVITY_REQUEST_CODE);

                break;

            case R.id.btn_save:
                Log.d("test","show msg call");
                //  showMessageDailogNextScreen("test","testing message",Login.class);
                   openActivity(MainActivity.class);
                   finish();
                //Intent intent = new Intent(this, AddAddressDailog.class);
                //startActivityForResult(intent, ADD_ADDRESS_ACTIVITY_REQUEST_CODE);

                break;






        }
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == ADD_ADDRESS_ACTIVITY_REQUEST_CODE) {
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

}
