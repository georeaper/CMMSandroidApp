package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.DataClasses.EquipmentCustomerSelect
import com.gkprojects.cmmsandroidapp.DataClasses.Hospital
import com.gkprojects.cmmsandroidapp.R


class RvAdapterFindCustomers(private val context: Context, private var customerList:ArrayList<EquipmentCustomerSelect>): RecyclerView.Adapter<RvAdapterFindCustomers.MyViewHolder>() {
    private var onClickListener: RvAdapterFindCustomers.OnClickListener? = null

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rv_id = itemView.findViewById<TextView>(R.id.list_tv_customer_id)
        val rv_customerName = itemView.findViewById<TextView>(R.id.list_tv_customer_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_rv_customer_find,parent,false)
        return RvAdapterFindCustomers.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
    fun setData(customerlist:ArrayList<EquipmentCustomerSelect>)
    {
        this.customerList=customerlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=customerList[position]
        holder.rv_id.setText(currentItem.hospitalID.toString())
        holder.rv_customerName.setText(currentItem.name)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentItem )
            }
        }

    }
    fun filterList(filterlist: ArrayList<EquipmentCustomerSelect>) {
        // below line is to add our filtered
        // list in our course array list.
        customerList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: EquipmentCustomerSelect)
    }
}