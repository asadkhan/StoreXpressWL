package com.example.irfan.storeexpressagas.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.OStatusPickupActivity;
import com.example.irfan.storeexpressagas.extras.ActivityManager;
import com.example.irfan.storeexpressagas.models.CustomerOrderResponse;

import java.util.List;


public class CompOrderListAdapter extends RecyclerView.Adapter<CompOrderListAdapter.ListViewHolder> {


    private Context mContext;

    private List<CustomerOrderResponse.Value> orders;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView txt_order_date,txt_order_id,txt_order_details_btn,txt_order_total;
        public Button btn_reorder;


        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);

            txt_order_date = (TextView) view.findViewById(R.id.txt_order_date);
            txt_order_id = (TextView) view.findViewById(R.id.txt_order_id);
            txt_order_details_btn = (TextView) view.findViewById(R.id.txt_order_details_btn);
            txt_order_total = (TextView) view.findViewById(R.id.txt_order_total);

            btn_reorder = (Button) view.findViewById(R.id.btn_reorder);

            txt_order_details_btn.setOnClickListener(this);

            btn_reorder.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            CustomerOrderResponse.Value orderObj = orders.get(getPosition());
            switch (view.getId()) {
                case R.id.txt_order_details_btn:
                    OStatusPickupActivity.orderid=orderObj.getOrderId();
                    ActivityManager.startActivity(view.getContext(),OStatusPickupActivity.class);
                    break;

            }


        }




    }


    public CompOrderListAdapter(List<CustomerOrderResponse.Value> ordersList) {
        this.orders = ordersList;

    }



    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_orders_pro, parent, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        CustomerOrderResponse.Value order = orders.get(position);


        holder.txt_order_date.setText(order.getOrderDate());
        holder.txt_order_id.setText(order.getOrderId().toString());
        holder.txt_order_total.setText(holder.txt_order_total.getText()+order.getTotalprice().toString());


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }








}
