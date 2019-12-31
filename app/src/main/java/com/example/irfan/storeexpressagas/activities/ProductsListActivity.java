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

import com.example.irfan.storeexpressagas.Adapters.CategoryListAdapter;
import com.example.irfan.storeexpressagas.Adapters.FproductListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.FproductResponse;
import com.example.irfan.storeexpressagas.models.FproductTwoCol;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    public TextView tv,txt_catname;
    public ImageView i;


    public RecyclerView recyclerViewFProduct;

    public FproductListAdapter mAdapterFproduct;

    public static String catName;

    public   List<FproductTwoCol> producListTwoCol = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);
        //parentBinding = binding;
        //loadViews();
        Log.d("test","calling test...");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product_list);
        setSupportActionBar(toolbar);
        MenuHandler.Activitycontextold=this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_product_list);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_product_list);
        navigationView.setNavigationItemSelectedListener(this);







        recyclerViewFProduct = (RecyclerView) findViewById(R.id.recycler_view_fProducts_list);

        mAdapterFproduct = new FproductListAdapter(this.producListTwoCol);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        recyclerViewFProduct.addItemDecoration(itemDecoration);

        recyclerViewFProduct.setHasFixedSize(true);
        recyclerViewFProduct.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFProduct.setAdapter(this.mAdapterFproduct);

        txt_catname=(TextView) findViewById(R.id.txt_catname);

        txt_catname.setText(catName);

        HideShowLogout(navigationView);
        // test();

        getProductsByCat();



    }




    public void getFproducts(){

        showProgress();
        Log.d("test","intestFproduct");
        RestClient.getAuthAdapter().getFeaturepProducts(0,0).enqueue(new GeneralCallBack<FproductResponse>(this) {
            @Override
            public void onSuccess(FproductResponse response) {

                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);
                producListTwoCol.clear();

                if (!response.getIserror()) {

                    List<FproductResponse.Value> iList = response.getValue();

                    for(int i=0; i< iList.size();i+=2){

                        if(i<iList.size()){
                            FproductTwoCol obj = new FproductTwoCol();
                            obj.ProductoneID=iList.get(i).getId();

                            Log.d("test",String.valueOf(iList.get(i).getId()));
                            obj.ProductoneName=iList.get(i).getName();
                            obj.ProductonePrice=iList.get(i).getPrice();
                            obj.ProductoneImg= iList.get(i).getImage();

                            if((i+1)<iList.size()){

                                obj.ProducttwoName=iList.get((i+1)).getName();
                                obj.ProducttwoPrice=iList.get((i+1)).getPrice();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoID=iList.get(i+1).getId();


                            }

                            producListTwoCol.add(obj);

                        }





                    }
                    mAdapterFproduct.notifyDataSetChanged();


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


    public void getProductsByCat(){

        showProgress();
        Log.d("test","intestFproduct");
        RestClient.getAuthAdapter().getProductsByCat(catName).enqueue(new GeneralCallBack<FproductResponse>(this) {
            @Override
            public void onSuccess(FproductResponse response) {

                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);
                producListTwoCol.clear();

                if (!response.getIserror()) {

                    List<FproductResponse.Value> iList = response.getValue();

                    for(int i=0; i< iList.size();i+=2){

                        if(i<iList.size()){
                            FproductTwoCol obj = new FproductTwoCol();
                            obj.ProductoneID=iList.get(i).getId();

                            Log.d("test",String.valueOf(iList.get(i).getId()));
                            obj.ProductoneName=iList.get(i).getName();
                            obj.ProductonePrice=iList.get(i).getPrice();
                            obj.ProductoneImg= iList.get(i).getImage();

                            if((i+1)<iList.size()){

                                obj.ProducttwoName=iList.get((i+1)).getName();
                                obj.ProducttwoPrice=iList.get((i+1)).getPrice();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoID=iList.get(i+1).getId();


                            }

                            producListTwoCol.add(obj);

                        }





                    }
                    mAdapterFproduct.notifyDataSetChanged();


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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_product_list);
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
          //  MenuHandler.logOut(this);
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
