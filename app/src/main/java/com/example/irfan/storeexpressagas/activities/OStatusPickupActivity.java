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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.CheckOutCartItemAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.MenuHandler;
import com.example.irfan.storeexpressagas.extras.Orders;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.OpeningTiming;
import com.example.irfan.storeexpressagas.models.OrderModel;
import com.example.irfan.storeexpressagas.models.OrderRequest;
import com.example.irfan.storeexpressagas.models.OrderResponse;
import com.example.irfan.storeexpressagas.models.PickupOrderDeatilResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OStatusPickupActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    public RecyclerView recyclerViewCheckoutItem;

    public CheckOutCartItemAdapter mAdapterCheckoutitem;
public PickupOrderDeatilResponse reOrderObj;
    public List<Cart> cartItemList = new ArrayList<>();
    public TextView tv,txt_orderID,txt_totalprice;
    public ImageView i;
    public static int orderid;
    public Button  btnOders,btn_reOrder;
    public LinearLayout lbl_pickup_warning;
    public TextView lbl_pickup_msg;
    public static int OrderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_pickup);

        lbl_pickup_msg=(TextView) findViewById(R.id.lbl_pickup_msg);

        lbl_pickup_warning=(LinearLayout) findViewById(R.id.lbl_pickup_warning);

        txt_orderID =(TextView) findViewById(R.id.txt_orderID) ;
        txt_totalprice =(TextView) findViewById(R.id.txt_totalprice) ;
 btnOders=(Button) findViewById(R.id.btn_openorders);
        btn_reOrder=(Button) findViewById(R.id.btn_reOrder);
        btn_reOrder.setOnClickListener(this);
        btnOders.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_osp);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_osp);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_osp);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerViewCheckoutItem = (RecyclerView) findViewById(R.id.recycler_view_orderstatusp);

        mAdapterCheckoutitem = new CheckOutCartItemAdapter(this.cartItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewCart.addItemDecoration(itemDecoration);
        OpeningTiming obj=GetShopOpeningTime();
        if(obj.showMSG){
            lbl_pickup_msg.setText(obj.MSG);

            lbl_pickup_warning.setVisibility(View.VISIBLE);

        }
        else{
            lbl_pickup_warning.setVisibility(View.GONE);

        }
        recyclerViewCheckoutItem.setHasFixedSize(true);
        recyclerViewCheckoutItem.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCheckoutItem.setAdapter(this.mAdapterCheckoutitem);
        HideShowLogout(navigationView);
        getOrderDetails();
//getCart();

    }


    public void getOrderDetails(){

        showProgress();

        Log.d("testme", String.valueOf(this.orderid));
        RestClient.getAuthAdapter().getPickupOrderDetails(this.orderid).enqueue(new GeneralCallBack<PickupOrderDeatilResponse>(this) {
            @Override
            public void onSuccess(PickupOrderDeatilResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("testme", Reslog);

                if(!response.getIserror()){

                    txt_orderID.setText(response.getValue().getOrderId().toString());
                    txt_totalprice.setText(txt_totalprice.getText()+response.getValue().getTotalprice().toString());

                    if(response.getValue().getOrderstatusID()==6 || response.getValue().getOrderstatusID()==10){


                        btn_reOrder.setVisibility(View.VISIBLE);
                    }
                    else{

                        btn_reOrder.setVisibility(View.GONE);
                    }

                    cartItemList.clear();
                    List<PickupOrderDeatilResponse.ItemsLst> cartlst=response.getValue().getItemsLst() ;
                    for(PickupOrderDeatilResponse.ItemsLst obj : cartlst){

                        Cart t = new Cart();
                        t.ItemQty=obj.getItemQty();
                        t.ItemID=obj.getItemId();
                       // t.ItemImg=obj.getItemName();
                        t.ItemPrice=Integer.valueOf(obj.getItemPrice());
                        t.ItemName=obj.getItemName();

                        cartItemList.add(t);

                    }
                    reOrderObj=response;
                    mAdapterCheckoutitem.notifyDataSetChanged();

                }

                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("testme",throwable.getMessage());

            }



        });
    }

public void reOrder(){

        Cart cart = new Cart();
    Cart.ClearCart(this);

    List<PickupOrderDeatilResponse.ItemsLst> cartlst=this.reOrderObj.getValue().getItemsLst() ;
    for(PickupOrderDeatilResponse.ItemsLst obj : cartlst){

        Cart t = new Cart();
        t.ItemQty=obj.getItemQty();
        t.ItemID=obj.getItemId();
        // t.ItemImg=obj.getItemName();
        t.ItemPrice=Integer.valueOf(obj.getItemPrice());
        t.ItemName=obj.getItemName();
        t.ItemImg=obj.getimageURL();
        Cart.addToCart(t.ItemID,t.ItemName,t.ItemPrice,t.ItemQty,this,t.ItemImg);


    }

openActivity(CartActivity.class);
}

    @Override
    public void onClick(View v) {
        Log.d("test","Next click");
        Log.d("test",String.valueOf(OrderRequest.OrderType));
        switch (v.getId()) {
            case R.id.btn_openorders:
               openActivity(OrdersActivity.class);
                Log.d("test","open Orders");
                break;
            case R.id.actionbar_notifcation_img:
                openActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                Log.d("test","show msg call");
                //  showMessageDailogNextScreen("test","testing message",Login.class);
                openActivity(CartActivity.class);
                break;

            case R.id.btn_reOrder:
                reOrder();
                break;


        }

    }

    public void getCart(){
        cartItemList.clear();
        List<Cart> cartlst=Cart.getCart(this);


        for(Cart obj : cartlst){
            Log.d("test","OBJ"+obj.ItemName);
            Cart t = new Cart();
            t.ItemQty=obj.ItemQty;
            t.ItemID=obj.ItemID;
            t.ItemImg=obj.ItemImg;
            t.ItemPrice=obj.ItemPrice;
            t.ItemName=obj.ItemName;

            cartItemList.add(t);

        }

        mAdapterCheckoutitem.notifyDataSetChanged();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_osp);
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
