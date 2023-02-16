package com.smartherd.globofly

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.smartherd.globofly.services.MessageService
import com.smartherd.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        message.text = "black friday! get 50% cash back on saving your first spot."

        if (isNetworkAvailable()!=true){
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show()
            return
        }

        // using alternate URL to retrieve data
        //retrieve data from different Server
         val messageService = ServiceBuilder.buildService(MessageService::class.java)
         val requestCall = messageService.getMessage("http://10.0.2.2:7000/messages")

        requestCall.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    val msg = response.body()
                    msg?.let {
                        message.text = msg
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Failed To Retrieve item", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to retrieve item" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })

        buttonget.setOnClickListener {
            getStated()
        }

    }

    fun getStated(){
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }
}