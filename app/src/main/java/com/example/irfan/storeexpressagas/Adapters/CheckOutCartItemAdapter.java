package com.example.irfan.storeexpressagas.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckOutCartItemAdapter extends RecyclerView.Adapter<CheckOutCartItemAdapter.ListViewHolder> {

    private List<Cart> itemList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtItemName,txtItemPrice;


        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);


            txtItemName = (TextView) view.findViewById(R.id.txt_item_name);

            txtItemPrice = (TextView) view.findViewById(R.id.txt_item_price);


        }



        @Override
        public void onClick(View view) {
            // Log.d("APITEST:", "onClick " + getPosition() + " " + mItem);


        }
    }





    public CheckOutCartItemAdapter(List<Cart> itemlist) {
        this.itemList = itemlist;

    }



    @Override
    public CheckOutCartItemAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_cart_item, parent, false);

        return new CheckOutCartItemAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CheckOutCartItemAdapter.ListViewHolder holder, int position) {
        Cart cartitem = itemList.get(position);


        String name=String.valueOf(position+1)+". " +cartitem.ItemName+ "     X "+ String.valueOf(cartitem.ItemQty);
        holder.txtItemName.setText(name);

        holder.txtItemPrice.setText("Rs "+String.valueOf(cartitem.ItemPrice));



        // formatting the date appropriately.


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
