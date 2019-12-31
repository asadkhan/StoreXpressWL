package com.example.irfan.storeexpressagas.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.activities.MainActivity;
import com.example.irfan.storeexpressagas.extras.AdapterCallback;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ListViewHolder> {


    private Context mContext;
    private AdapterCallback.OnItemClickListener clickListener;
    private List<CategoryResponse.catValue> categories;
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public ImageButton catImg;
        public TextView    txtCatName;


        private String mItem;
        private TextView mTextView;
        public ListViewHolder(View view) {
            super(view);
           // view.setOnClickListener(this);

            catImg = (ImageButton) view.findViewById(R.id.imgCat);
            txtCatName = (TextView) view.findViewById(R.id.txtCat);
            catImg.setOnClickListener(this);
            txtCatName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
             clickListener.onClick(view, getPosition());
        }




    }


    public CategoryListAdapter(List<CategoryResponse.catValue> categoriesList) {
        this.categories = categoriesList;

    }



    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cate_list, parent, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        CategoryResponse.catValue catObj = categories.get(position);

        String imgURL=catObj.getImage();
        holder.txtCatName.setText(catObj.getName());

        Picasso.with(holder.catImg.getContext()).load(imgURL).resize(60, 60).centerCrop().into(holder.catImg);

        // formatting the date appropriately.


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setClickListener(AdapterCallback.OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }






}
