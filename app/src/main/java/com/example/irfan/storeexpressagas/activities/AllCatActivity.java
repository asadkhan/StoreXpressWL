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
import com.example.irfan.storeexpressagas.Adapters.CategoryListAllCatAdapter;
import com.example.irfan.storeexpressagas.Adapters.FproductListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.AdapterCallback;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.FproductTwoCol;
import com.example.irfan.storeexpressagas.network.RestClient;

import java.util.ArrayList;
import java.util.List;

public class AllCatActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    public RecyclerView recyclerViewAllCat;

    public CategoryListAllCatAdapter mAdapterCat;
    public TextView tv;
    public ImageView i;
private NavigationView navigationView;


    public List<CategoryResponse.catValue> catList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_allcat);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_allcat);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view_allcat);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerViewAllCat = (RecyclerView) findViewById(R.id.recycler_view_allcat);

        mAdapterCat = new CategoryListAllCatAdapter(this.catList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewAllCat.addItemDecoration(itemDecoration);

        recyclerViewAllCat.setHasFixedSize(true);
        recyclerViewAllCat.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAllCat.setAdapter(this.mAdapterCat);

        HideShowLogout(navigationView);

getCategories();


    }




    @Override
    public void onClick(View v) {
        Log.d("test","click");


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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_allcat);
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


    public void getCategories(){

        showProgress();
        Log.d("test","intest");
        RestClient.getAuthAdapter().getCategories().enqueue(new GeneralCallBack<CategoryResponse>(this) {
            @Override
            public void onSuccess(CategoryResponse response) {

                hideProgress();

                if (!response.getIserror()) {



                    catList.clear();
                    List<CategoryResponse.catValue> list = response.getValue();
                    for(CategoryResponse.catValue obj : list){

                        Log.d("test",obj.getName());
                        Log.d("test",obj.getImage());
                        catList.add(obj);
                    }




                    mAdapterCat.notifyDataSetChanged();

                    //getFproducts();

                }
                else{



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
