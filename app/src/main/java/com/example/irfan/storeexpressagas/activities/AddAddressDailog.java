package com.example.irfan.storeexpressagas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.baseclasses.BaseActivity;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.ValidationUtility;
import com.example.irfan.storeexpressagas.models.AddAddressRequest;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.OrderModel;
import com.example.irfan.storeexpressagas.models.OrderResponse;
import com.example.irfan.storeexpressagas.models.ProductReqResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

public class AddAddressDailog extends BaseActivity implements View.OnClickListener {
    private RadioGroup radioAddressGroup;
    private RadioButton radioAddressButton;
    private Button bt_add,btn_Cancel;
    private EditText et_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_add_address);

        radioAddressGroup = (RadioGroup) findViewById(R.id.radioGroupAddress);

        bt_add=(Button) findViewById(R.id.bt_add);
        btn_Cancel=(Button) findViewById(R.id.btn_Cancel);
        et_address=(EditText) findViewById(R.id.et_address);
        bt_add.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.bt_add:

                Add();

                break;

            case R.id.btn_Cancel:
                calcel();
                break;



        }
    }

    private boolean isValidate() {
        if (!ValidationUtility.edittextValidator(et_address)) {
            return false;
        }

        return true;
    }
    public void Add(){
    if(!isValidate()){

        return;
    }

        int selectedId = radioAddressGroup.getCheckedRadioButtonId();
        radioAddressButton = (RadioButton) findViewById(selectedId);

        showProgress();
        AddAddressRequest obj = new  AddAddressRequest();
        obj.setAddress(et_address.getText().toString());


        obj.setAddressType(radioAddressButton.getText());

        Gson gson = new Gson();
        String Reslog= gson.toJson(obj);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).addAddress(obj).enqueue(new GeneralCallBack<ProductReqResponse>(this) {
            @Override
            public void onSuccess(ProductReqResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("testme", Reslog);

                if(!response.getIserror()){
                    String stringToPassBack ="1";

                    // put the String to pass back into an Intent and close this activity
                    Intent intent = new Intent();
                    intent.putExtra("Added", stringToPassBack);
                    setResult(RESULT_OK, intent);
                    finish();

                }

                else{

                    Toast.makeText(AddAddressDailog.this,response.getMessage(), Toast.LENGTH_SHORT).show();
                }
                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");
                Toast.makeText(AddAddressDailog.this,throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }



        });



    }

public void calcel(){

    String stringToPassBack ="0";

    // put the String to pass back into an Intent and close this activity
    Intent intent = new Intent();
    intent.putExtra("Added", stringToPassBack);
    setResult(RESULT_OK, intent);
    finish();


}
}
