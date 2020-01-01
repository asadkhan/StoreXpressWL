package com.example.irfan.storeexpressagas.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.OStatusPickupActivity;
import com.example.irfan.storeexpressagas.activities.OrderStatusDeliveryActivity;
import com.example.irfan.storeexpressagas.activities.ProductActivity;
import com.example.irfan.storeexpressagas.extras.ActivityManager;
import com.example.irfan.storeexpressagas.extras.AdapterCallback;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.CustomerOrderResponse;
import com.example.irfan.storeexpressagas.models.FproductTwoCol;
import com.squareup.picasso.Picasso;

import java.util.List;



public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ListViewHolder> {


    private Context mContext;

    private List<CustomerOrderResponse.Value> orders;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView txt_order_date,txt_order_id,txt_order_details_btn,txt_order_status;


        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);

            txt_order_date = (TextView) view.findViewById(R.id.txt_order_date);
            txt_order_id = (TextView) view.findViewById(R.id.txt_order_id);
            txt_order_details_btn = (TextView) view.findViewById(R.id.txt_order_details_btn);
            txt_order_status = (TextView) view.findViewById(R.id.txt_order_status);

            txt_order_details_btn.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            CustomerOrderResponse.Value orderObj = orders.get(getPosition());
            switch (view.getId()) {
                case R.id.txt_order_details_btn:

                   if(orderObj.getOrderType()==1) {
                       OStatusPickupActivity.orderid = orderObj.getOrderId();
                       ActivityManager.startActivity(view.getContext(), OStatusPickupActivity.class);
                       break;
                   }
                    if(orderObj.getOrderType()==2) {
                        OrderStatusDeliveryActivity.orderid = orderObj.getOrderId();
                        ActivityManager.startActivity(view.getContext(), OrderStatusDeliveryActivity.class);
                        break;
                    }


            }

        }




    }


    public OrderListAdapter(List<CustomerOrderResponse.Value> ordersList) {
        this.orders = ordersList;

    }



    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_current_order, parent, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        CustomerOrderResponse.Value order = orders.get(position);


        holder.txt_order_date.setText(order.getOrderDate());
        holder.txt_order_id.setText(order.getOrderId().toString());
        holder.txt_order_status.setText(order.getOrderStatus());


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }








}
