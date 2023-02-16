package com.smartherd.globofly.services

import com.smartherd.globofly.model.Destination
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface DestinationServices {

    // using this to get all the list
    @GET("destination")
    fun getDestinationlist(): Call<List<Destination>>

    //using query to sort for country named india
    @GET("destination")
    fun getDestinationlist(@Query("country")country: String): Call<List<Destination>>

    //using QueryMap to retrieve country name, also using count to get a single country
    //HashMap to get string
    // LET modify the code by adding header statically
    // @Headers("x-device-type: Android")// we will remove statical header if we want use custom Interceptor with header we need remove this line of code for header
    @GET("destination")
    fun getDestinationlist(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>
    //to use dynamically header will need to add our header in the function eg fun getDestination(@QueryMap filter:
    //HashMap<String, String>, @Header("Accept-Language")language: String): Call<List<Destination>>
    // but while you want to use custom Interceptor with header you need remove the header you added dynamically


    //using path, TO retrieve data from server
    @GET("destination/{id}")
    fun getDestination(@Path("id")id: Int): Call<Destination>


    @POST("destionation")
    fun addDestination(@Body newDestination: Destination): Call<Destination>


    //using put to update or replace existing data from web server
    //using FormUrlEncoded format
    //make sure the key you used is same with the key in web server
    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
            @Path("id")id: Int,
            @Field("city")city: String,
            @Field("description")description: String,
            @Field("country")country: String
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id")id: Int): Call<Unit>}