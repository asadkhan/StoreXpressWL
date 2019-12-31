package com.example.irfan.storeexpressagas.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.CategoryListAdapter;
import com.example.irfan.storeexpressagas.Adapters.FproductListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.backgroundservices.NotificationsService;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.databinding.*;
import com.example.irfan.storeexpressagas.extras.AdapterCallback;
import com.example.irfan.storeexpressagas.extras.Constants;
import com.example.irfan.storeexpressagas.extras.DeviceDatabaseHandler;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.FproductResponse;
import com.example.irfan.storeexpressagas.models.FproductTwoCol;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterCallback.OnItemClickListener,View.OnClickListener{
    public RecyclerView recyclerViewCat;
    public RecyclerView recyclerViewFProduct;
    public CategoryListAdapter mAdapterCat;
    public FproductListAdapter mAdapterFproduct;
    public TextView tv;
    public ImageView i;
    public   List<CategoryResponse.catValue> catList = new ArrayList<>();
    private  int skip=0,take=20;
    public   List<FproductTwoCol> producListTwoCol = new ArrayList<>();
    final private int  REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;
    public NavigationView navigationView;
public LinearLayout layno_internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_by_cate);
        layno_internet =(LinearLayout) findViewById(R.id.layno_internet);
        //parentBinding = binding;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.inflateMenu(R.menu.navigation_menu);
        setSupportActionBar(toolbar);

        MenuHandler.Activitycontextold=this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GetPermissions();

        recyclerViewCat = (RecyclerView) findViewById(R.id.recycler_view_cat);

        mAdapterCat = new CategoryListAdapter(this.catList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewCat.addItemDecoration(itemDecoration);

        recyclerViewCat.setHasFixedSize(false);
        recyclerViewCat.setLayoutManager(horizontalLayoutManagaer);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapterCat.setClickListener(this);
        recyclerViewCat.setAdapter(this.mAdapterCat);




        recyclerViewFProduct = (RecyclerView) findViewById(R.id.recycler_view_fProducts);

        mAdapterFproduct = new FproductListAdapter(this.producListTwoCol);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        recyclerViewFProduct.addItemDecoration(itemDecoration);

        recyclerViewFProduct.setHasFixedSize(true);
        recyclerViewFProduct.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFProduct.setAdapter(this.mAdapterFproduct);

        HideShowLogout(navigationView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //  this.startForegroundService(new Intent(this, LocationService.class));
         //   this.startForegroundService(new Intent(this, NotificationsService.class));
          //  startForegroundService(new Intent(this, NotificationsService.class));
        } else {
            //startService(new Intent(this, NotificationsService.class));
        }


        // test();
if(haveNetworkConnection()){
showNointernet(false);
    getCategories();
}
    else{
    showNointernet(true);

}




    }

    public void showNointernet(boolean show) {
        if (show) {
            layno_internet.setVisibility(View.VISIBLE);
            recyclerViewFProduct.setVisibility(View.GONE);
        }
        else{
            layno_internet.setVisibility(View.GONE);
            recyclerViewFProduct.setVisibility(View.VISIBLE);


        }
    }




    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.actionbar_notifcation_img:
            openActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:

                //  showMessageDailogNextScreen("test","testing message",Login.class);
                openActivity(CartActivity.class);
                break;



        }
    }

@Override
    public void onClick(View view, int position) {

       CategoryResponse.catValue cat = catList.get(position);
        getProductsByCat(cat.getName());

    }
    public void getCategories(){
        showProgress();

        RestClient.getAuthAdapter().getCategories().enqueue(new GeneralCallBack<CategoryResponse>(this) {
            @Override
            public void onSuccess(CategoryResponse response) {

                hideProgress();

                if (!response.getIserror()) {



                    catList.clear();
                    List<CategoryResponse.catValue> list = response.getValue();
                    for(CategoryResponse.catValue obj : list){


                        catList.add(obj);
                    }




                    mAdapterCat.notifyDataSetChanged();

                    getFproducts();

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


    public void getFproducts(){

        showProgress();

        RestClient.getAuthAdapter().getFeaturepProducts(skip,take).enqueue(new GeneralCallBack<FproductResponse>(this) {
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
                            obj.ProductoneDesc=iList.get(i).getDescription().toString();
                            obj.ProductoneQty=iList.get(i).getTotalQuantity();
                            if((i+1)<iList.size()){

                                obj.ProducttwoName=iList.get((i+1)).getName();
                                obj.ProducttwoPrice=iList.get((i+1)).getPrice();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoImg=iList.get((i+1)).getImage();
                                obj.ProducttwoID=iList.get(i+1).getId();

                                obj.ProducttwoDesc=iList.get(i+1).getDescription().toString();
                                obj.ProducttwoQty=iList.get(i+1).getTotalQuantity();


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
                getCategories();
            }



        });

    }


    public void getProductsByCat(String catName){

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
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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



    @TargetApi(Build.VERSION_CODES.M)
    private void GetPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  {

            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP){
                List<String> permissionsNeeded = new ArrayList<String>();

                final List<String> permissionsList = new ArrayList<String>();
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("GPS");
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("Location");

                if (!addPermission(permissionsList, Manifest.permission.CAMERA))
                    permissionsNeeded.add("Camera");

                if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Write_External_Storage");
                if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Read_External_Storage");


                if (permissionsList.size() > 0) {
                    if (permissionsNeeded.size() > 0) {


                    }
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    return;
                }

            }




            return;
        }

        else{


        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


                        ) {
                    // All Permissions Granted

                    // setMapForV6();

                } else {
                    // Permission Denied
                    // Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                    //       .show();

                    showMessageDailog(getString(R.string.app_name), Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
