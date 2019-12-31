package com.example.irfan.storeexpressagas.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.CartActivity;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemListAdapter.ListViewHolder>{

    private Context mContext;

    public void setItemListAdapterContext(Context context){


        this.mContext=context;
    }
    private List<Cart> itemList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public TextView txtItemName,txtQtyBox,txtItemPrice,txtItemRemove;
        public Button btnPlus,btnMinus;

        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);

            img = (ImageView) view.findViewById(R.id.cartitem_img);
            txtItemName = (TextView) view.findViewById(R.id.txt_cartitem_name);
            txtQtyBox = (TextView) view.findViewById(R.id.txt_cartitem_qty_box);
            txtItemPrice = (TextView) view.findViewById(R.id.txt_cartitem_price);
            txtItemRemove = (TextView) view.findViewById(R.id.txt_cartitem_remove);
            btnPlus = (Button) view.findViewById(R.id.btn_cart_plus);
            btnMinus = (Button) view.findViewById(R.id.btn_cartitem_minus);
            txtItemRemove.setOnClickListener(this);
            btnMinus.setOnClickListener(this);
            btnPlus.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
            // Log.d("APITEST:", "onClick " + getPosition() + " " + mItem);
            Cart cartitem = itemList.get(getPosition());
            Cart cart = new Cart();
            int total;
            switch (view.getId()) {

                case R.id.btn_cart_plus:
                    int qty= Integer.valueOf(txtQtyBox.getText().toString());
                    qty++;
                    if(qty <1){
                        qty=1;

                    }
                     total=qty*cartitem.ItemPrice;
                    txtQtyBox.setText(String.valueOf(qty));
                    txtItemPrice.setText("Rs. "+String.valueOf(total));
                    cart.addToCartFromCart(cartitem.ItemID,cartitem.ItemName,cartitem.ItemPrice,qty,view.getContext(),cartitem.ItemImg);

                    if (mContext instanceof CartActivity) {
                        // CartActivity.TotalPrice = (CartActivity.TotalPrice-removedItem.ItemPrice);
                        Log.d("test","show msg call2");
                        ((CartActivity) mContext).UpdateCartAfterRemove();
                    }
                    break;

                case R.id.btn_cartitem_minus:
                    Log.d("test","show msg call");
                    //  showMessageDailogNextScreen("test","testing message",Login.class);
                     qty= Integer.valueOf(txtQtyBox.getText().toString());
                    qty--;
                    if(qty <1){
                        qty=1;

                    }

                    total=qty*cartitem.ItemPrice;
                    txtQtyBox.setText(String.valueOf(qty));
                    txtItemPrice.setText("Rs. "+String.valueOf(total));


                    cart.addToCartFromCart(cartitem.ItemID,cartitem.ItemName,cartitem.ItemPrice,qty,view.getContext(),cartitem.ItemImg);

                    if (mContext instanceof CartActivity) {
                        // CartActivity.TotalPrice = (CartActivity.TotalPrice-removedItem.ItemPrice);
                        Log.d("test","show msg call2");
                        ((CartActivity) mContext).UpdateCartAfterRemove();
                    }
                    break;

                case R.id.txt_cartitem_remove:
                    Log.d("test","show msg call");
                    //  showMessageDailogNextScreen("test","testing message",Login.class);
                    int newPosition = getAdapterPosition();
                    Cart removedItem=itemList.get(newPosition);
                    Cart.removeCartITem(itemList.get(newPosition).ItemID,view.getContext());
                    itemList.remove(newPosition);
                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition, itemList.size());


                    if (mContext instanceof CartActivity) {
                  // CartActivity.TotalPrice = (CartActivity.TotalPrice-removedItem.ItemPrice);
                        Log.d("test","show msg call2");
                        ((CartActivity) mContext).UpdateCartAfterRemove();
                    }
                    break;



            }



        }


    }





    public CartItemListAdapter(List<Cart> itemlist) {
        this.itemList = itemlist;

    }



    @Override
    public CartItemListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new CartItemListAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartItemListAdapter.ListViewHolder holder, int position) {
        Cart cartitem = itemList.get(position);


        String imgURL=cartitem.ItemImg;
        holder.txtItemName.setText(cartitem.ItemName);
        holder.txtQtyBox.setText(String.valueOf(cartitem.ItemQty));
        holder.txtItemPrice.setText(holder.txtItemPrice.getText()+String.valueOf(cartitem.ItemPrice));


        try {
            Picasso.with(holder.img.getContext()).load(imgURL).resize(250, 250).centerCrop().into(holder.img);
        }
        catch (Exception e){


        }
        // formatting the date appropriately.


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }





}
