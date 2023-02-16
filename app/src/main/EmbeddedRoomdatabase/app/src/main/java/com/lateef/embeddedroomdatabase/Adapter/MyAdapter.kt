package com.lateef.embeddedroomdatabase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lateef.embeddedroomdatabase.R
import com.lateef.embeddedroomdatabase.data.Person
import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var personList = emptyList<Person>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return personList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.firstname_txt.text = personList[position].firstname
        holder.itemView.lastname_txt.text = personList[position].lastname
        holder.itemView.age_txt.text = personList[position].age.toString()

        holder.itemView.streetname_txt.text = personList[position].address.streetName
        holder.itemView.streetnumber_txt.text = personList[position].address.streetNumber.toString()

       /* holder.itemView.rowLayout.setOnClickListener {
            // We are passing our user object to our Update Fragment
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }*/
    }

    fun setData(person: List<Person>){
        this.personList = person
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}