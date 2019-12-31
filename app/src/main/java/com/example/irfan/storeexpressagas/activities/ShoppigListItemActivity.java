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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.Adapters.ShoppingListAdapter;
import com.example.irfan.storeexpressagas.Adapters.ShoppingListItemAdapter;
import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.CustomAutoCompleteTextChangedListener;
import com.example.irfan.storeexpressagas.extras.CustomAutoCompleteView;
import com.example.irfan.storeexpressagas.extras.ValidationUtility;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.ShoppingList;
import com.example.irfan.storeexpressagas.models.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppigListItemActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    public TextView tv;
    public ImageView i;

    public static int ListId=0;
    public static String ListName="";
   public CustomAutoCompleteView et_list_item_name;

   public ImageButton  btn_list_item_add;
public TextView txt_list_name;

    public RecyclerView recyclerViewShoppigListItem;

    public ShoppingListItemAdapter mAdapter;
   public ArrayAdapter<String> myAdapterAutoC;

    public List<ShoppingListItem> ItemList = new ArrayList<>();


    public String[] item = new String[] {"Please search..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_items);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spli);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_spli);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_spli);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerViewShoppigListItem = (RecyclerView) findViewById(R.id.recycler_view_ShoppingList_item);

        mAdapter = new ShoppingListItemAdapter(this.ItemList);
        mAdapter.setShoppingListItemAdapterContext(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerViewCart.addItemDecoration(itemDecoration);

        recyclerViewShoppigListItem.setHasFixedSize(true);
        recyclerViewShoppigListItem.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewShoppigListItem.setAdapter(this.mAdapter);

        txt_list_name=(TextView) findViewById(R.id.txt_list_name) ;
        et_list_item_name=(CustomAutoCompleteView) findViewById(R.id.et_list_item_name) ;
        btn_list_item_add=(ImageButton) findViewById(R.id.btn_list_item_add) ;
        txt_list_name.setText(ListName);
        btn_list_item_add.setOnClickListener(this);
        HideShowLogout(navigationView);

        et_list_item_name.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
        myAdapterAutoC = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        et_list_item_name.setAdapter(myAdapterAutoC);
        LoadList();
    }

    public void add(){
        Log.d("testme","Inadd call");
        if(isValidate()){

            ShoppingList.addListItem(this.ListId,et_list_item_name.getText().toString(),this);

            ShoppingList.addListItemHst(et_list_item_name.getText().toString(),this);
            Log.d("testme","Inadd after vali");
            et_list_item_name.setText("");
            LoadList();


        }


    }


    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<ShoppingListItem> products = ShoppingList.getAutoComplete(searchTerm,this);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (ShoppingListItem record : products) {

            item[x] = record.itemName;
            x++;
        }

        return item;
    }


    private boolean isValidate() {
        if (!ValidationUtility.edittextValidator(et_list_item_name)) {
            return false;
        }


        return true;
    }


    public void remove(int itemID){
        ShoppingList.removeShoppingListItem(itemID,this);
        LoadList();

    }


    public void LoadList(){
        this.ItemList.clear();
        List<ShoppingListItem> lst = ShoppingList.getShoppingListItem(this.ListId,this);

        for(ShoppingListItem obj : lst){

            this.ItemList.add(obj);


        }
        mAdapter.notifyDataSetChanged();


    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_spli);
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
            case R.id.btn_list_item_add:
               add();
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
    public void onBackPressed()
    {
       openActivity(ShoppingListActivity.class);
        // code here to show dialog
     //   super.onBackPressed();  // optional depending on your needs
    }
}
