package com.example.irfan.storeexpressagas.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.ShoppigListItemActivity;
import com.example.irfan.storeexpressagas.activities.ShoppingListActivity;
import com.example.irfan.storeexpressagas.models.ShoppingList;
import com.example.irfan.storeexpressagas.models.ShoppingListItem;

import java.util.List;



public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ListViewHolder>{

    private Context mContext;

    public void setShoppingListItemAdapterContext(Context context){


        this.mContext=context;
    }
    private List<ShoppingListItem> itemList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_sp_item_name;
        public ImageButton btn_sp_item_delete,btn_mark;


        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);
            txt_sp_item_name = (TextView) view.findViewById(R.id.txt_sp_item_name);

            btn_sp_item_delete = (ImageButton) view.findViewById(R.id.btn_sp_item_delete);

            btn_sp_item_delete.setOnClickListener(this);

            btn_mark=(ImageButton) view.findViewById(R.id.btn_mark);
            btn_mark.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
            // Log.d("APITEST:", "onClick " + getPosition() + " " + mItem);
         final   ShoppingListItem obj = itemList.get(getPosition());

            switch (view.getId()) {

                case R.id.btn_sp_item_delete:


                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Do you want to remove list item ?");
                    //builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();


                            if (mContext instanceof ShoppigListItemActivity) {
                                // CartActivity.TotalPrice = (CartActivity.TotalPrice-removedItem.ItemPrice);
                                Log.d("test","show msg call2");
                                ((ShoppigListItemActivity) mContext).remove(obj.itemId);
                            }



                            // stop chronometer here

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(view.getContext(), R.color.black));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(view.getContext(), R.color.black));


                    break;

                case R.id.btn_mark:
                int mark=0;
                if(obj.mark==0){
                    obj.mark=1;
                    mark=1;
                }
                else{
                    obj.mark=0;
                    mark=0;

                }

                ShoppingList.setItemMark(obj.itemId,mark,view.getContext());

                notifyDataSetChanged();
                    break;

            }



        }


    }





    public ShoppingListItemAdapter(List<ShoppingListItem> itemlist) {
        this.itemList = itemlist;

    }



    @Override
    public ShoppingListItemAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_pro, parent, false);

        return new ShoppingListItemAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShoppingListItemAdapter.ListViewHolder holder, int position) {
        ShoppingListItem obj = itemList.get(position);


        holder.txt_sp_item_name.setText(obj.itemName);

        if(obj.mark==1){
            holder.txt_sp_item_name.setTextColor(Color.RED);
        holder.txt_sp_item_name.setPaintFlags(holder.txt_sp_item_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.txt_sp_item_name.setPaintFlags(0);
            holder.txt_sp_item_name.setTextColor(Color.BLACK);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }





}