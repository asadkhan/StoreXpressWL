package com.example.irfan.storeexpressagas.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.ProductActivity;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.ActivityManager;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.FproductResponse;
import com.example.irfan.storeexpressagas.models.FproductTwoCol;
import com.example.irfan.storeexpressagas.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FproductListAdapter  extends RecyclerView.Adapter<FproductListAdapter.ListViewHolder>{

    private List<FproductTwoCol> productsList;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img1,img2;
        public TextView TxtPrice1,TxtPrice2,TxtName1,TxtName2,TxtAddToCart,TxtAddToCartTwo,TxtPriceLabel;
       public LinearLayout Ladd ,Ladd1,layproone,layprotwo ;

        private String mItem;

        public ListViewHolder(View view) {
            super(view);
           // view.setOnClickListener(this);

            img1 = (ImageView) view.findViewById(R.id.Img_Product1);
            img2 = (ImageView) view.findViewById(R.id.ImgProduct2);
           // TxtPriceLabel=view.findViewById(R.id.txt_label);
            TxtPrice1 = (TextView) view.findViewById(R.id.txt_price1);
            TxtPrice2 = (TextView) view.findViewById(R.id.txt_price2);
            TxtName1 = (TextView) view.findViewById(R.id.txt_name1);
            TxtName2 = (TextView) view.findViewById(R.id.txt_name2);
            TxtAddToCart=(TextView) view.findViewById(R.id.txt_add);
            TxtAddToCartTwo=(TextView) view.findViewById(R.id.txt_AddTwo);
             Ladd = (LinearLayout )view.findViewById(R.id.ladd);
            Ladd1 = (LinearLayout )view.findViewById(R.id.ladd);
            Ladd.setOnClickListener(this);
            Ladd1.setOnClickListener(this);

            layproone = (LinearLayout )view.findViewById(R.id.layproone);
            layprotwo = (LinearLayout )view.findViewById(R.id.layprotwo);
            layproone.setOnClickListener(this);
            layprotwo.setOnClickListener(this);

            img1.setOnClickListener(this);
            TxtName1.setOnClickListener(this);
            img2.setOnClickListener(this);
            TxtAddToCart.setOnClickListener(this);
            TxtAddToCartTwo.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            Log.d("test:", "click");

            if(view.getId()==R.id.Img_Product1 || view.getId()==R.id.layproone ) {
              FproductTwoCol   mobj = productsList.get(getPosition());

                Product obj =new Product();
                obj.img=mobj.ProductoneImg;
                obj.name=mobj.ProductoneName;
                obj.price=mobj.ProductonePrice;
                obj.itemID=mobj.ProductoneID;
                obj.desc=mobj.ProductoneDesc;
                obj.qty=mobj.ProductoneQty;
                ProductActivity.obj=null;
                ProductActivity.obj=obj;
                ActivityManager.startActivity(view.getContext(),ProductActivity.class);

                Log.d("test:", "image view click");
            }

            if(view.getId()==R.id.ImgProduct2 || view.getId()==R.id.layprotwo ) {
                //   obj = trackableOrderList.get(getPosition());
                FproductTwoCol   mobj = productsList.get(getPosition());
                Product obj =new Product();
                obj.itemID=mobj.ProducttwoID;
                obj.img=mobj.ProducttwoImg;
                obj.desc=mobj.ProducttwoDesc;
                obj.qty=mobj.ProducttwoQty;
                obj.name=mobj.ProducttwoName;
                obj.price=mobj.ProducttwoPrice;
                ProductActivity.obj=null;
                ProductActivity.obj=obj;
                ActivityManager.startActivity(view.getContext(),ProductActivity.class);


                Log.d("test:", "image view click");
            }



            if(view.getId()==R.id.txt_name1) {
                Log.d("test:", "name click");
            }


            if(view.getId()==R.id.txt_add) {
                Log.d("test:", "add cart");
                FproductTwoCol   mobj = productsList.get(getPosition());
                Cart item = new Cart();

                if(mobj.ProductoneID !=0){
                    item.ItemID=mobj.ProductoneID;
                    item.ItemName=mobj.ProductoneName;
                    item.ItemImg=mobj.ProductoneImg;

                    item.ItemPrice=Integer.valueOf(mobj.ProductonePrice);
                    item.ItemQty=1;


                }


                item.addToCart(item.ItemID,item.ItemName,item.ItemPrice,item.ItemQty,view.getContext(),item.ItemImg);
                //showMessageToast(view.getResources().getString(R.string.msg_add_to_car));
                Toast.makeText(view.getContext(),view.getResources().getString(R.string.msg_add_to_car) ,
                        Toast.LENGTH_LONG).show();
            }

            if(view.getId()==R.id.txt_AddTwo){
                Log.d("test:", "add cart");
                FproductTwoCol   mobj = productsList.get(getPosition());
                Cart item = new Cart();


                if(mobj.ProducttwoID !=0){
                    item.ItemID=mobj.ProducttwoID;
                    item.ItemName=mobj.ProducttwoName;
                    item.ItemImg=mobj.ProducttwoImg;
                    item.ItemPrice=Integer.valueOf(mobj.ProducttwoPrice);
                    item.ItemQty=1;


                }

                item.addToCart(item.ItemID,item.ItemName,item.ItemPrice,item.ItemQty,view.getContext(),item.ItemImg);
                //showMessageToast(view.getResources().getString(R.string.msg_add_to_car));
                Toast.makeText(view.getContext(),view.getResources().getString(R.string.msg_add_to_car) ,
                        Toast.LENGTH_LONG).show();
            }






        }


    }


    public FproductListAdapter(List<FproductTwoCol> fProductsList) {
        this.productsList = fProductsList;

    }



    @Override
    public FproductListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cate_pro_list, parent, false);

        return new FproductListAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FproductListAdapter.ListViewHolder holder, int position) {
        FproductTwoCol Obj = productsList.get(position);

        String imgURL1=Obj.ProductoneImg;
        String imgURL2=Obj.ProducttwoImg;

        holder.TxtName1.setText(Obj.ProductoneName);
        holder.TxtName2.setText(Obj.ProducttwoName);

        holder.TxtPrice1.setText("Rs "+Obj.ProductonePrice);
        holder.TxtPrice2.setText("Rs "+Obj.ProducttwoPrice);
      //  holder.TxtPriceLabel.setText("Rs. ");
        if(imgURL1 !=null && !imgURL1.isEmpty()  ){

            holder.img1.setBackgroundResource(android.R.color.transparent);
        }

        if(imgURL2 !=null && !imgURL2.isEmpty()  ){

            holder.img2.setBackgroundResource(android.R.color.transparent);
        }
        Picasso.with(holder.img1.getContext()).load(imgURL1).resize(800, 800).centerCrop().into(holder.img1);
        Picasso.with(holder.img2.getContext()).load(imgURL2).resize(800, 800).centerCrop().into(holder.img2);

        // formatting the date appropriately.

        if(Obj.ProducttwoPrice==null){

         //   holder.layprotwo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }




}
