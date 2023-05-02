package com.gkprojects.cmmsandroidapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Customers
import com.gkprojects.cmmsandroidapp.R
import kotlin.collections.ArrayList


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var customerList= ArrayList<Customers>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.customerlist_rv,parent,false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=customerList[position]
        holder.namename.text=currentitem.name
        holder.address.text=currentitem.address
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentitem )
            }
        }
    }

    fun updateCustomerList(customerList :List<Customers>){
        this.customerList.clear()
        this.customerList.addAll(customerList)
        notifyDataSetChanged()

    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Customers)
    }






    class MyViewHolder(itemview : View) :RecyclerView.ViewHolder(itemview){

        val namename =itemview.findViewById<TextView>(R.id.tv_customer)
        val address =itemview.findViewById<TextView>(R.id.tv_detail1)

    }

}