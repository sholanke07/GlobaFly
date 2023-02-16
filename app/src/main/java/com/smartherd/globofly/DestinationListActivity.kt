package com.smartherd.globofly


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.smartherd.globofly.helper.DestinationAdapter
import com.smartherd.globofly.helper.SampleData
import com.smartherd.globofly.model.Destination
import com.smartherd.globofly.services.DestinationServices
import com.smartherd.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_list.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class DestinationListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_list)


        fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {

       // destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val destinationService = ServiceBuilder.buildService(DestinationServices::class.java)



        //using QueryMap with HashMap to retrieve data
        /* val filter = HashMap<String, String>()
        filter["country"] = "India"
        filter["count"] = "1"

        val requestCall = destinationService.getDestinationlist(filter)*/


        //getting data from server without filter
         val requestCall = destinationService.getDestinationlist("India")
        //to cancel request u can use any of the two functions
        requestCall.cancel()
        requestCall.isCanceled

        //to make a network call asynchronously
            //enqueue is used to implement a callback interface
        //we are getting data from the server
        requestCall.enqueue(object: Callback<List<Destination>> {
            override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
                if (response.isSuccessful){
                    val destinationList = response.body()!!
                    destiny_recycler_view.adapter = DestinationAdapter(destinationList)

                } else if(response.code() == 401) {
                    Toast.makeText(this@DestinationListActivity, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()

                }else{// Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@DestinationListActivity, "Failed to retrieve items", Toast.LENGTH_SHORT).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Toast.makeText(this@DestinationListActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }

        })

    }
}