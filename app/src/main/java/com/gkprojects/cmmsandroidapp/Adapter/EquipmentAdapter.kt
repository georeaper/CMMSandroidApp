package com.gkprojects.cmmsandroidapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkprojects.cmmsandroidapp.DataClasses.Equipment
import com.gkprojects.cmmsandroidapp.R

class EquipmentAdapter(private val context: Context, private var equipmentlist:List<Equipment>):RecyclerView.Adapter<EquipmentAdapter.MyViewHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.customerlist_rv,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return equipmentlist.size
    }
    fun setData(equipmentlist:ArrayList<Equipment>)
    {
        this.equipmentlist=equipmentlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentitem = equipmentlist[position]
        holder.titlesn.setText(currentitem.serialNumber)
        holder.model.setText(currentitem.model)
        holder.category.setText(currentitem.category)
        holder.customer.setText(currentitem.hospitalID.toString())
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titlesn = itemView.findViewById<TextView>(R.id.tv_equip_sn)
        val model =itemView.findViewById<TextView>(R.id.tv_equip_model)
        val category =itemView.findViewById<TextView>(R.id.tv_equip_category)
        val customer =itemView.findViewById<TextView>(R.id.tv_equip_customer)


    }
}