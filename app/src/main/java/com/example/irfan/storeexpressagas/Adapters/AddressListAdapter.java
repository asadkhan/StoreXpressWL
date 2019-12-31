package com.example.irfan.storeexpressagas.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.CategoryResponse;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ListViewHolder>{
    private List<AddressResponse.Value> address;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtEdit ,txt_address;
        public RadioButton radioAdress;



        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);

            radioAdress = (RadioButton) view.findViewById(R.id.radio_address);
            txtEdit = (TextView) view.findViewById(R.id.txt_edit);
            txt_address = (TextView) view.findViewById(R.id.txt_address);
            radioAdress.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            AddressResponse.Value obj=address.get(getPosition());

            Log.d("APITEST:", "onClick " + obj.getAddress() + " " + mItem);



        }


    }


    public AddressListAdapter(List<AddressResponse.Value> addressList) {
        this.address = addressList;

    }



    @Override
    public AddressListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adress, parent, false);

        return new AddressListAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressListAdapter.ListViewHolder holder, int position) {
        AddressResponse.Value Obj = address.get(position);

        //holder.radioAdress.setText(String.valueOf(Obj.getId()));

        holder.txt_address.setText(Obj.getAddress());

        //  Picasso.with(holder.catImg.getContext()).load(imgURL).resize(60, 60).centerCrop().into(holder.catImg);

        // formatting the date appropriately.


    }

    @Override
    public int getItemCount() {
        return address.size();
    }


}
