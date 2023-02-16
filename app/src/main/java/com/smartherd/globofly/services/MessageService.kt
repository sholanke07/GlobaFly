package com.smartherd.globofly.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    //using alternate URL Get to get string
    @GET
    fun getMessage(@Url anotherUrl: String): Call<String>
}