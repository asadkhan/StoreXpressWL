package com.example.irfan.storeexpressagas.network;

public class EndPoints {

    static final String API_PREFIX="/api/";
    static final String CATEGORIES=API_PREFIX+"category/getcategories";
    static final String REGISTRATION=API_PREFIX+"accounts/createappuser";
    static final String TEST=API_PREFIX+"cart/updatecart";
    static final String LOGIN="/oauth/token";
    static final String FPRODUCT=API_PREFIX+"item/featureproducts";
    static final String PRODUCTBYCAT=API_PREFIX+"item/GetItem";
    static final String PLACEORDER=API_PREFIX+"order/placeorder";
    static final String PICKUPORDERDETAILS=API_PREFIX+"Order/GetPickUpOrderDeatils";
    static final String DELIVERYORDERDETAILS=API_PREFIX+"Order/GetDeliveryOrderDeatils";
    static final String ADRESSES=API_PREFIX+"useraddress/getuseraddresses";
    static final String CUSTOMERSORDERS=API_PREFIX+"order/GetCustomerOrders";
    static final String ADDPRODUCTREQUEST=API_PREFIX+"item/addProductRequest";
    static final String GETPROFILE=API_PREFIX+"accounts/GetProfile";
    static final String REMOVEADDRESS=API_PREFIX+"useraddress/removeUserAddress";
    static final String ADDADDRESS=API_PREFIX+"useraddress/adduseraddress";
    static final String GETNOTIFICATION=API_PREFIX+"notification/GetNotification";
    static final String UPDATEDEVICEINFO=API_PREFIX+"notification/addUpdatefcmtoken";

}
