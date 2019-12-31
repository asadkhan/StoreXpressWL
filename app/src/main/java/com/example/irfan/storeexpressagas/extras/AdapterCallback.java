package com.example.irfan.storeexpressagas.extras;

import android.view.View;

import com.example.irfan.storeexpressagas.models.CategoryResponse;

public interface AdapterCallback {

    void onClickCallback(CategoryResponse.catValue itemModel);

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
