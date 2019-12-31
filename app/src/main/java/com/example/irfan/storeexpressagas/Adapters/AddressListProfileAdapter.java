package com.example.irfan.storeexpressagas.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.activities.CartActivity;
import com.example.irfan.storeexpressagas.activities.CheckOutFirstActivity;
import com.example.irfan.storeexpressagas.activities.OStatusPickupActivity;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.OrderModel;
import com.example.irfan.storeexpressagas.models.OrderResponse;
import com.example.irfan.storeexpressagas.models.ProductReqResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.List;

import static com.example.irfan.storeexpressagas.extras.PreLoader.show;


public class AddressListProfileAdapter extends RecyclerView.Adapter<AddressListProfileAdapter.ListViewHolder>{
    private List<AddressResponse.Value> address;
    private BaseActivity mConext;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_add_type ,txt_address,txt_address_remove;




        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);


            txt_add_type = (TextView) view.findViewById(R.id.txt_add_type);
            txt_address = (TextView) view.findViewById(R.id.txt_address);
            txt_address_remove = (TextView) view.findViewById(R.id.txt_address_remove);
            txt_address_remove.setOnClickListener(this);
        }



        @Override
        public void onClick(final View view) {
           final AddressResponse.Value obj=address.get(getPosition());


            Log.d("APITEST:", "onClick " + obj.getAddress() + " " + mItem);

            switch (view.getId()) {

                case R.id.txt_address_remove:


                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Do you want to remove address ?");
                    //builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();




                            RestClient.getAuthAdapterToekn(Auth.getToken(view.getContext())).removeAddress(obj).enqueue(new GeneralCallBack<ProductReqResponse>(mConext) {
                                @Override
                                public void onSuccess(ProductReqResponse response) {
                                    Gson gson = new Gson();
                                    String Reslog= gson.toJson(response);
                                    Log.d("testme", Reslog);

                                    if(!response.getIserror()){


                                        Toast.makeText(view.getContext(),response.getMessage(),
                                                Toast.LENGTH_LONG).show();

                                        int newPosition = getAdapterPosition();
                                        AddressResponse.Value removedItem=address.get(newPosition);

                                        address.remove(newPosition);
                                        notifyItemRemoved(newPosition);
                                        notifyItemRangeChanged(newPosition, address.size());
                                    }
                                    else{

                                        Toast.makeText(view.getContext(),response.getMessage(),
                                                Toast.LENGTH_LONG).show();

                                    }





                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    //onFailure implementation would be in GeneralCallBack class

                                    Log.d("test","failed");

                                }



                            });

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



            }

        }


    }


    public AddressListProfileAdapter(List<AddressResponse.Value> addressList,BaseActivity c) {
        this.address = addressList;
        this.mConext=c;
    }



    @Override
    public AddressListProfileAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_profile, parent, false);

        return new AddressListProfileAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressListProfileAdapter.ListViewHolder holder, int position) {
        AddressResponse.Value Obj = address.get(position);


        holder.txt_add_type.setText(String.valueOf(Obj.getAddressType()));
        holder.txt_address.setText(String.valueOf(Obj.getAddress()));
        //holder.radioAdress.setText(String.valueOf(Obj.getId()));

      //  holder.txt_address.setText(Obj.getAddress());

        //  Picasso.with(holder.catImg.getContext()).load(imgURL).resize(60, 60).centerCrop().into(holder.catImg);

        // formatting the date appropriately.


    }

    @Override
    public int getItemCount() {
        return address.size();
    }


}
