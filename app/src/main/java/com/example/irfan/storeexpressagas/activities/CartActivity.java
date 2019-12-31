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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.CartItemListAdapter;
import com.example.irfan.storeexpressagas.Adapters.CategoryListAdapter;
import com.example.irfan.storeexpressagas.Adapters.FproductListAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.extras.PrefManager;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    public RecyclerView recyclerViewCart;

    public CartItemListAdapter mAdapterCart;
    public Button btnCheckout;

    public static int TotalPrice=0;
    public PrefManager sharedperference;
    public List<Cart> cartItemList = new ArrayList<>();
    public TextView tv,text_total_price,txt_msg,txt_total_item;
    public ImageView i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sharedperference=new PrefManager(this);
        btnCheckout=(Button) findViewById(R.id.btn_checkout);
        txt_total_item=(TextView) findViewById(R.id.txt_total_item);
        text_total_price= (TextView) findViewById(R.id.text_total_price);
        txt_msg=(TextView) findViewById(R.id.txt_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cart);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_cart);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerViewCart = (RecyclerView) findViewById(R.id.recycler_view_cart);

        mAdapterCart = new CartItemListAdapter(this.cartItemList);
        mAdapterCart.setItemListAdapterContext(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewCart.addItemDecoration(itemDecoration);

        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCart.setAdapter(this.mAdapterCart);
        getCart();
        btnCheckout.setOnClickListener(this);
        HideShowLogout(navigationView);
    }



    @Override
    public void onClick(View v) {
        Log.d("test","click");
        switch (v.getId()) {
            case R.id.btn_checkout:

                checkOut();
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


    public void checkOut(){
        Intent i;

        if(sharedperference.getToken()==null || sharedperference.getToken()=="") {
            i = new Intent(CartActivity.this, Login.class);
            sharedperference.saveLogin("checkout");
        }
        else{
            i = new Intent(CartActivity.this, CheckOutFirstActivity.class);

        }

        //i = new Intent(SplashScreen.this, LanuageSelection.class);


        startActivity(i);
        finish();



    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cart);
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


    public void getCart(){
        CartActivity.TotalPrice=0;
        cartItemList.clear();
        List<Cart> cartlst=Cart.getCart(this);
        txt_total_item.setText(String.valueOf(cartlst.size()));
       // cartItemList=cartlst;

        for(Cart obj : cartlst){
            Log.d("test","OBJ"+obj.ItemName);
            Cart t = new Cart();
            t.ItemQty=obj.ItemQty;
            t.ItemID=obj.ItemID;
            t.ItemImg=obj.ItemImg;
            t.ItemPrice=obj.ItemPrice;
            t.ItemName=obj.ItemName;
            CartActivity.TotalPrice=CartActivity.TotalPrice+t.ItemPrice;

            cartItemList.add(t);

        }
   UpdateTotal();
        mAdapterCart.notifyDataSetChanged();

        setEmptyCartScreen(cartlst.size());
    }



    private void setEmptyCartScreen(int cartCount){

        if(cartCount > 0){
            recyclerViewCart.setVisibility(View.VISIBLE);
            btnCheckout.setVisibility(View.VISIBLE);

            txt_msg.setVisibility(View.GONE);


        }else{

            btnCheckout.setVisibility(View.GONE);

            //text_add.setVisibility(View.GONE);


            txt_msg.setVisibility(View.VISIBLE);
            recyclerViewCart.setVisibility(View.GONE);
            Log.d("data","empty");
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


    public  void UpdateTotal(){
        text_total_price.setText("Rs. "+String.valueOf(CartActivity.TotalPrice));

    }


    public void UpdateCartAfterRemove(){

        CartActivity.TotalPrice=0;
        Log.d("test","Updateremove"+CartActivity.TotalPrice);
        List<Cart> cartlst=Cart.getCart(this);
        txt_total_item.setText(String.valueOf(cartlst.size()));
        // cartItemList=cartlst;
        tv.setText(String.valueOf(cartlst.size()));
        for(Cart obj : cartlst){

            Cart t = new Cart();
            t.ItemQty=obj.ItemQty;
            t.ItemID=obj.ItemID;
            t.ItemImg=obj.ItemImg;
            t.ItemPrice=obj.ItemPrice;
            t.ItemName=obj.ItemName;
            CartActivity.TotalPrice=CartActivity.TotalPrice+t.ItemPrice;



        }

        Log.d("test","UpdateremoveEnd"+CartActivity.TotalPrice);
        UpdateTotal();

        setEmptyCartScreen(cartlst.size());


    }
}
