package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.R

class CustomerAdapter(private val context: Context, private var customerList:ArrayList<Hospital>): RecyclerView.Adapter<CustomerAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.customerlist_rv,parent,false)
        return CustomerAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    fun setData(customerList:ArrayList<Hospital>)
    {
        this.customerList=customerList
        notifyDataSetChanged()
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Hospital)
    }
//    fun getPosition(position :Int){
//        return
//    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = customerList[position]
        holder.customerName.setText(currentitem.name)
        holder.customerAddress.setText(currentitem.address)
        val pos=holder.absoluteAdapterPosition
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentitem )
            }
        }

    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val customerName = itemView.findViewById<TextView>(R.id.tv_customer)
        val customerAddress =itemView.findViewById<TextView>(R.id.tv_detail1)


    }
}