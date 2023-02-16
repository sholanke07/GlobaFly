package com.smartherd.globofly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.smartherd.globofly.helper.SampleData
import com.smartherd.globofly.model.Destination
import com.smartherd.globofly.services.DestinationServices
import com.smartherd.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_details)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

                val id = intent.getIntExtra(ARG_ITEM_ID, 0)

                loadDetails(id)


                initUpdateButton(id)

                initDeleteButton(id)

        }
    }
    private fun loadDetails(id: Int) {


             val destinationService = ServiceBuilder.buildService(DestinationServices::class.java)
             val requestCall = destinationService.getDestination(id)

             requestCall.enqueue(object: retrofit2.Callback<Destination>{

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful){
                    val destination = response.body()
                    destination?.let {
                        et_city.setText(destination.city)
                        et_description.setText(destination.description)
                        et_country.setText(destination.country)
                    }
                }else{
                    Toast.makeText(this@DestinationDetailActivity, "failed to retrieve data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Toast.makeText(this@DestinationDetailActivity, "Failed" + t.toString(), Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {
            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

           /* val destination = Destination()
            destination.id = id
            destination.city = city
            destination.description = description
            destination.country = country*/


            val destinationService = ServiceBuilder.buildService(DestinationServices::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)

            requestCall.enqueue(object: Callback<Destination>{
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful){
                        finish()
                        var updatedDestination = response.body() // use it if you want to extract the data,ignore if don't want to
                        Toast.makeText(this@DestinationDetailActivity, "Sucessfully Updated", Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this@DestinationDetailActivity, "Failed to updata", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Failed" + t.toString(), Toast.LENGTH_LONG).show()
                }

            })


        }
    }

    private fun initDeleteButton(id: Int){

        btn_delete.setOnClickListener {

            val destinatonService = ServiceBuilder.buildService(DestinationServices::class.java)
            val requestCall = destinatonService.deleteDestination(id)

            requestCall.enqueue(object: Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@DestinationDetailActivity, "Successfully deleted", Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this@DestinationDetailActivity, "Failed to delete item", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Failed" + t.toString(), Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
