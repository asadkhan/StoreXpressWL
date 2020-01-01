package com.example.irfan.storeexpressagas.network;
import com.example.irfan.storeexpressagas.models.AddAddressRequest;
import com.example.irfan.storeexpressagas.models.AddressResponse;
import com.example.irfan.storeexpressagas.models.CartRequest;
import com.example.irfan.storeexpressagas.models.CategoryResponse;
import com.example.irfan.storeexpressagas.models.CustomerOrderResponse;
import com.example.irfan.storeexpressagas.models.DeliveryOrderDeatilResponse;
import com.example.irfan.storeexpressagas.models.DeviceInfoRequest;
import com.example.irfan.storeexpressagas.models.DeviceInfoResponse;
import com.example.irfan.storeexpressagas.models.FproductResponse;
import com.example.irfan.storeexpressagas.models.GResponse;
import com.example.irfan.storeexpressagas.models.GeneralResponse;
import com.example.irfan.storeexpressagas.models.LoginResponse;
import com.example.irfan.storeexpressagas.models.NotificationResponse;
import com.example.irfan.storeexpressagas.models.OrderModel;
import com.example.irfan.storeexpressagas.models.OrderRequest;
import com.example.irfan.storeexpressagas.models.OrderResponse;
import com.example.irfan.storeexpressagas.models.PickupOrderDeatilResponse;
import com.example.irfan.storeexpressagas.models.ProductReqRequest;
import com.example.irfan.storeexpressagas.models.ProductReqResponse;
import com.example.irfan.storeexpressagas.models.ProfileDetailsResponse;
import com.example.irfan.storeexpressagas.models.ProfileResponse;
import com.example.irfan.storeexpressagas.models.RegistrationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface WebCalls {


    @GET(EndPoints.PRODUCTBYCAT)
    Call<FproductResponse> getProductsByCat(@Query("categoryname") String categoryname);



    @GET(EndPoints.PICKUPORDERDETAILS)
    Call<PickupOrderDeatilResponse> getPickupOrderDetails(@Query("id") int id);

    @GET(EndPoints.DELIVERYORDERDETAILS)
    Call<DeliveryOrderDeatilResponse> getDeliveryOrderDetails(@Query("id") int id);



    @GET(EndPoints.CUSTOMERSORDERS)
    Call<CustomerOrderResponse> getCustomerOrders();


    @GET(EndPoints.ADRESSES)
    Call<AddressResponse> getAddresses();


    @GET(EndPoints.GETNOTIFICATION)
    Call<NotificationResponse> getNotifications();





    @GET(EndPoints.CATEGORIES)
    Call<CategoryResponse> getCategories();


    @POST(EndPoints.TEST)
    Call<GResponse> test(@Body CartRequest cart);

    @POST(EndPoints.PLACEORDER)
    Call<OrderResponse> placeORder(@Body OrderModel order);



    @GET(EndPoints.GETPROFILE)
    Call<ProfileDetailsResponse> getProfile();

    @GET(EndPoints.FPRODUCT)
    Call<FproductResponse> getFeaturepProducts(@Query("skip") int skip, @Query("take") int take);


    @FormUrlEncoded
    @POST(EndPoints.LOGIN)
    Call<LoginResponse> loginUser(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("grant_type") String grant_type);



   // @Headers("Content-Type: application/json")
    @POST(EndPoints.REGISTRATION)
    Call<GResponse> registerUser(@Body RegistrationRequest regUser);

    @POST(EndPoints.ADDPRODUCTREQUEST)
    Call<ProductReqResponse> addProductRequest(@Body ProductReqRequest productReq);

    @POST(EndPoints.REMOVEADDRESS)
    Call<ProductReqResponse> removeAddress(@Body AddressResponse.Value addressReq);

    @POST(EndPoints.ADDADDRESS)
    Call<ProductReqResponse> addAddress(@Body AddAddressRequest addressReq);


    @POST(EndPoints.UPDATEDEVICEINFO)
    Call<DeviceInfoResponse> updateDeviceInfo(@Body DeviceInfoRequest deviceInfo);



}
