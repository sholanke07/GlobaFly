package com.lateef.embeddedroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lateef.embeddedroomdatabase.Adapter.MyAdapter
import com.lateef.embeddedroomdatabase.data.Address
import com.lateef.embeddedroomdatabase.data.MyViewModel
import com.lateef.embeddedroomdatabase.data.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val adapter by lazy { MyAdapter() }
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val address = Address("Ogunnipebi", 18)
        val person = Person(0, "John", "Doe", 25, address)
        myViewModel.insertPerson(person)

        myViewModel.readPerson.observe(this,{
            adapter.setData(it)
        })
    }
}