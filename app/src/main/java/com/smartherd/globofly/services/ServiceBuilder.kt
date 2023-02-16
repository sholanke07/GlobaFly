package com.smartherd.globofly.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private const val URL = "http://10.0.2.2:9000/"
    //private const val URL = "http://127.0.0.1:9000/"

    //create logging
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create a custom interceptor to apply headers application
   /* val headerInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()

            val response = chain.proceed(request)
            return response
        }

    }*/


    //create OkHttp Client
    /*private val okHttp = OkHttpClient.Builder().apply {
        //here we are using the call time to set time out for the network operation to 5sec
        callTimeout(5, TimeUnit.SECONDS)
        //adding custom Interceptor with header
        addInterceptor(headerInterceptor)
        //we need to add interceptor
        addInterceptor(logger)
    }.build()*/

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    //create retrofit builder
     private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())// to convert Gson to destination object
        .client(okHttp.build())

    //create instance of retrofit
    private val retrofit = builder.build()
    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)

        }





}