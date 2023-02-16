package com.smartherd.globofly

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.smartherd.globofly.helper.SampleData
import com.smartherd.globofly.model.Destination
import com.smartherd.globofly.services.DestinationServices
import com.smartherd.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btn_add.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = et_city.text.toString()
            newDestination.description = et_description.text.toString()
            newDestination.country = et_country.text.toString()


            //we are send data to the server
            val destinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
            val requestCall = destinationServices.addDestination(newDestination)

            requestCall.enqueue(object: Callback<Destination>{
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful){
                        finish()
                        var newlrCreatedDestination = response.body() //you can use it or ignore it depending on ur choice
                        Toast.makeText(this@DestinationCreateActivity, "Successfully Added", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DestinationCreateActivity, "Failed To Add", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DestinationCreateActivity, "Failed" + t.toString(), Toast.LENGTH_LONG).show()
                }

            })


        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }
}