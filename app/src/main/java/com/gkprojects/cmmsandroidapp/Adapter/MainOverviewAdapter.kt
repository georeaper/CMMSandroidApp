package com.gkprojects.cmmsandroidapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.gkprojects.cmmsandroidapp.DataClasses.OverviewMainData
import com.gkprojects.cmmsandroidapp.R
import org.w3c.dom.Text

class MainOverviewAdapter(context: Context, private var list:ArrayList<OverviewMainData>) : RecyclerView.Adapter<MainOverviewAdapter.MyViewHolder>(){
    class MyViewHolder(itemView :View): RecyclerView.ViewHolder(itemView) {
        val title : TextView=itemView.findViewById(R.id.textViewTitle_main)
        val customerName : TextView=itemView.findViewById(R.id.textViewCustomerName_main)
        val id : TextView=itemView.findViewById(R.id.textViewId_main)
        val date : TextView=itemView.findViewById(R.id.textViewDate_main)

    }
    fun setData(tempList: ArrayList<OverviewMainData>) {
        this.list=tempList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.list_main_rv_overview,parent,false)
        return MainOverviewAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = list[position]
        holder.title.text=currentitem.information
        holder.customerName.text=currentitem.customerName
        holder.id.text=currentitem.id
        holder.date.text=currentitem.date

        if (currentitem.setTable == "Contracts"){
            val orangeColor = ContextCompat.getColor(holder.itemView.context, R.color.orange)
            holder.title.setTextColor(orangeColor)
            holder.customerName.setTextColor(orangeColor)
            holder.id.setTextColor(orangeColor)
            holder.date.setTextColor(orangeColor)

        }else{
            val redColor = ContextCompat.getColor(holder.itemView.context, R.color.red)
            holder.title.setTextColor(redColor)
            holder.customerName.setTextColor(redColor)
            holder.id.setTextColor(redColor)
            holder.date.setTextColor(redColor)


        }

    }
}