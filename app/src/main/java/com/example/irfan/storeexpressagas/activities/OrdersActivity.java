package com.example.irfan.storeexpressagas.activities;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.CategoryListAllCatAdapter;
import com.example.irfan.storeexpressagas.Adapters.CompOrderListAdapter;
import com.example.irfan.storeexpressagas.Adapters.OrderListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.CustomerOrderResponse;
import com.example.irfan.storeexpressagas.network.RestClient;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    public TextView tv;
    public ImageView i;

    public RecyclerView recyclerViewCurrentOrders;

    public OrderListAdapter mAdapterCurrentOrders;

    public RecyclerView recyclerViewCompOrders;

    public CompOrderListAdapter mAdapterCompOrders;

    public List<CustomerOrderResponse.Value> orders= new ArrayList<>();

    public List<CustomerOrderResponse.Value> Comporders= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_orders);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_orders);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_orders);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerViewCurrentOrders = (RecyclerView) findViewById(R.id.recycler_view_currentOrders);

        mAdapterCurrentOrders = new OrderListAdapter(this.orders);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);

        recyclerViewCurrentOrders.setHasFixedSize(true);
        recyclerViewCurrentOrders.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCurrentOrders.setAdapter(this.mAdapterCurrentOrders);



        recyclerViewCompOrders = (RecyclerView) findViewById(R.id.recycler_view_compOrders);

        mAdapterCompOrders = new CompOrderListAdapter(this.Comporders);
       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);
        RecyclerView.LayoutManager mLayoutManagerCompOrders = new LinearLayoutManager(getApplicationContext());
        recyclerViewCompOrders.setHasFixedSize(true);
        recyclerViewCompOrders.setLayoutManager(mLayoutManagerCompOrders);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCompOrders.setAdapter(this.mAdapterCompOrders);

        HideShowLogout(navigationView);
        getOrders();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_orders);
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

        return  true;
    }



    public void getOrders(){

        showProgress();
        Log.d("test","intest");
        RestClient.getAuthAdapterToekn(Auth.getToken(this)).getCustomerOrders().enqueue(new GeneralCallBack<CustomerOrderResponse>(this) {
            @Override
            public void onSuccess(CustomerOrderResponse response) {

                hideProgress();

                if (!response.getIserror()) {



                    orders.clear();
                    Comporders.clear();
                    List<CustomerOrderResponse.Value> list = response.getValue();
                    for(CustomerOrderResponse.Value obj : list){

                        if(obj.getOrderstatusID() ==2 || obj.getOrderstatusID()==9) {
                            orders.add(obj);
                        }

                        if(obj.getOrderstatusID() ==6 || obj.getOrderstatusID()==10) {
                            Comporders.add(obj);
                        }


                    }




                    mAdapterCurrentOrders.notifyDataSetChanged();

                    mAdapterCompOrders.notifyDataSetChanged();

                    //getFproducts();

                }
                else{



                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test",throwable.getMessage());

            }



        });



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

    }
