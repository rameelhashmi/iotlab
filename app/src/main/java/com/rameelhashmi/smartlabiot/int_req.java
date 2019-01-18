
//This is interface class use to server act as a directory where the Php webservices stored
package com.rameelhashmi.smartlabiot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface int_req {

    @POST("android/server/")//Local server directory
    Call<resp_serv> process(@Body req_serv request);// request and response fro server

}
































